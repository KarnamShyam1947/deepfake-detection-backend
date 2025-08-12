package ai.deepdetect.consumer;

import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

import ai.deepdetect.dto.UserResponse;
import ai.deepdetect.services.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserActivationConsumer {

    private final EmailService emailService;

    @RetryableTopic(
        attempts = "3",
        backoff = @Backoff(
            delay = 2000,
            maxDelay = 1500   
        )
    )
    @KafkaListener(topics = "user-activation-topic", groupId = "user-activation-group")
    public void consume(
        UserResponse userResponse,
        @Header(KafkaHeaders.RECEIVED_TOPIC) String topicName,
        @Header(KafkaHeaders.OFFSET) String offset) {
        
        log.info("Consumed : {} from topic {} and offset was : {}", userResponse.toString(), topicName, offset);

        emailService.sendActivationEmail(userResponse);
    }

    @DltHandler
    public void dltHandler(
        UserResponse userResponse,
        @Header(KafkaHeaders.RECEIVED_TOPIC) String topicName,
        @Header(KafkaHeaders.OFFSET) String offset
    ) {
        log.info("Consumed : {} from topic {} and offset was : {}", userResponse.toString(), topicName, offset);
    }   
}
