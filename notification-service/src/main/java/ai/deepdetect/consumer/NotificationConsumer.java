package ai.deepdetect.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import ai.deepdetect.dto.event.NotificationEvent;
import ai.deepdetect.services.EmailService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NotificationConsumer {

    private final EmailService emailService;

    @KafkaListener(topics = "notification-request-topic", groupId = "notification-request-group")
    public void consume(NotificationEvent notificationEvent) {
        System.out.println("Consumed : " + notificationEvent.toString());

        emailService.sendEmail(notificationEvent);
    }
}
