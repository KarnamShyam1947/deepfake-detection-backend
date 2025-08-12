package ai.deepdetect.services;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import ai.deepdetect.entities.UserEntity;
import io.github.springwolf.bindings.kafka.annotations.KafkaAsyncOperationBinding;
import io.github.springwolf.core.asyncapi.annotations.AsyncOperation;
import io.github.springwolf.core.asyncapi.annotations.AsyncPublisher;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserActivateProducer {

    @Value("${application.kafka.topic-name}")
    private String topicName;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @AsyncPublisher(
        operation = @AsyncOperation(
            channelName = "${application.kafka.topic-name}",
            description = "this topic send user activation event to the active user account when registration was completed"
        )
    )
    @KafkaAsyncOperationBinding
    public void sendNotificationEvent(UserEntity userEntity) {
        CompletableFuture<SendResult<String, Object>> send = kafkaTemplate.send(topicName, userEntity);

        send.whenComplete((result, e) -> {
            if (e == null) 
                System.out.println("Send details : [" + userEntity + "]");

            else
                System.out.println("Error : " + e.getMessage());
            
        });
    }
    
}
