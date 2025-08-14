package ai.deepdetect.consumer;

import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

import ai.deepdetect.dto.event.NotificationEvent;
import ai.deepdetect.services.EmailService;
import io.github.springwolf.bindings.kafka.annotations.KafkaAsyncOperationBinding;
import io.github.springwolf.core.asyncapi.annotations.AsyncListener;
import io.github.springwolf.core.asyncapi.annotations.AsyncOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationConsumer {

    private final EmailService emailService;


    @KafkaAsyncOperationBinding
    @RetryableTopic(attempts = "3", backoff = @Backoff(delay = 2000, maxDelay = 1500))
    @AsyncListener(operation = @AsyncOperation(channelName = "notification-request-topic", payloadType = NotificationEvent.class))
    @KafkaListener(topics = "notification-request-topic", groupId = "notification-request-group")
    public void consume(
            @Payload NotificationEvent notificationEvent,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topicName,
            @Header(KafkaHeaders.OFFSET) String offset) {

        log.info("Consumed : {} from topic {} and offset was : {}", notificationEvent.toString(), topicName, offset);

        emailService.sendEmail(notificationEvent);
    }

    @DltHandler
    public void dltHandler(
            @Payload NotificationEvent notificationEvent,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topicName,
            @Header(KafkaHeaders.OFFSET) String offset) {
        log.info("Consumed : {} from topic {} and offset was : {}", notificationEvent.toString(), topicName, offset);
    }

}
