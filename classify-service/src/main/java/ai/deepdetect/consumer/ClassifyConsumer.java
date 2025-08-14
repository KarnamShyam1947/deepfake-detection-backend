package ai.deepdetect.consumer;
import org.springframework.beans.BeanUtils;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;
import ai.deepdetect.clients.HistoryClient;
import ai.deepdetect.dto.ExternalAPIResponse;
import ai.deepdetect.dto.HistoryResponse;
import ai.deepdetect.dto.event.ClassifyEvent;
import ai.deepdetect.dto.event.NotificationEvent;
import ai.deepdetect.services.ExternalAPIService;
import ai.deepdetect.services.KafkaProducerService;
import io.github.springwolf.bindings.kafka.annotations.KafkaAsyncOperationBinding;
import io.github.springwolf.core.asyncapi.annotations.AsyncListener;
import io.github.springwolf.core.asyncapi.annotations.AsyncOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClassifyConsumer {
    private final KafkaProducerService kafkaProducerService;
    private final ExternalAPIService externalAPIService;
    // private final HistoryService historyService;
    private final HistoryClient historyClient;

    @SuppressWarnings("null")
    @KafkaAsyncOperationBinding
    @RetryableTopic(attempts = "3", backoff = @Backoff(delay = 2000, maxDelay = 1500))
    @AsyncListener(operation = @AsyncOperation(channelName = "classification-request-topic", payloadType = ClassifyEvent.class))
    @KafkaListener(topics = "classification-request-topic", groupId = "classification-request-group")
    public void consume(@Payload ClassifyEvent classifyEvent, 
                       @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                       @Header(KafkaHeaders.OFFSET) long offset) {
        try {
            log.info("Consumer consumed : {}, From topic {} and offset was {}", classifyEvent.toString(), topic,
                    offset);
            // REST CALL : Call the python api for actual prediction
            ExternalAPIResponse apiResponse = externalAPIService.classifyVideo(classifyEvent);
            HistoryResponse updateHistoryRecord = historyClient
                    .handleUpdateRequest("Bearer " + classifyEvent.getAuthToken(), apiResponse).getBody();
            NotificationEvent notificationEvent = new NotificationEvent();
            BeanUtils.copyProperties(updateHistoryRecord, notificationEvent);
            notificationEvent.setAuthToken(classifyEvent.getAuthToken());
            // produce event to notification topic to different topic
            kafkaProducerService.sendNotificationEvent(notificationEvent);
            log.info("Classification Completed");
        } catch (Exception e) {
            log.error("Error processing classification event: ", e);
        }
    }

    @DltHandler
    public void listenDLT(@Payload ClassifyEvent classifyEvent, 
                         @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                         @Header(KafkaHeaders.OFFSET) long offset) {
        log.warn("DLT Received : {} , from {} , offset {}", classifyEvent.toString(), topic, offset);
    }
}