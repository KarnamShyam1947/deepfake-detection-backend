package ai.deepdetect.consumer;

import org.springframework.beans.BeanUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import ai.deepdetect.clients.HistoryClient;
import ai.deepdetect.dto.ExternalAPIResponse;
import ai.deepdetect.dto.HistoryResponse;
import ai.deepdetect.dto.event.ClassifyEvent;
import ai.deepdetect.dto.event.NotificationEvent;
import ai.deepdetect.services.ExternalAPIService;
// import ai.deepdetect.services.HistoryService;
import ai.deepdetect.services.KafkaProducerService;
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
    @KafkaListener(topics = "classification-request-topic", groupId = "classification-request-group")
    public void consume(ClassifyEvent classifyEvent) {
        try{
            System.out.println("Consumer consumed : " + classifyEvent.toString());

            // REST CALL : Call the python api for actual prediction 
            ExternalAPIResponse apiResponse = externalAPIService.classifyVideo(classifyEvent);

            HistoryResponse updateHistoryRecord = historyClient.handleUpdateRequest("Bearer "+classifyEvent.getAuthToken(), apiResponse).getBody();

            NotificationEvent notificationEvent = new NotificationEvent();
            BeanUtils.copyProperties(updateHistoryRecord, notificationEvent);
            notificationEvent.setAuthToken(classifyEvent.getAuthToken());

            // produce event to notification topic to different topic
            kafkaProducerService.sendNotificationEvent(notificationEvent);

            log.info("Classification Completed");
        }
        catch(Exception e) {
            System.out.println(e.toString());
        }
    }
}
