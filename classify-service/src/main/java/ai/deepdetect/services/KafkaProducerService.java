package ai.deepdetect.services;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import ai.deepdetect.dto.event.NotificationEvent;
import io.github.springwolf.bindings.kafka.annotations.KafkaAsyncOperationBinding;
import io.github.springwolf.core.asyncapi.annotations.AsyncOperation;
import io.github.springwolf.core.asyncapi.annotations.AsyncPublisher;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    @Value("${application.kafka.topic-name}")
    private String topicName;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @AsyncPublisher(
        operation = @AsyncOperation(
            channelName = "${application.kafka.topic-name}",
            description = "this topic send notification event to the notify user that prediction was completed"
        )
    )
    @KafkaAsyncOperationBinding
    public void sendNotificationEvent(NotificationEvent notificationEvent) {
        CompletableFuture<SendResult<String, Object>> send = kafkaTemplate.send(topicName, notificationEvent);

        send.whenComplete((result, e) -> {
            if (e == null) 
                System.out.println("Send details : [" + notificationEvent + "]");

            else
                System.out.println("Error : " + e.getMessage());
            
        });
    }
}
