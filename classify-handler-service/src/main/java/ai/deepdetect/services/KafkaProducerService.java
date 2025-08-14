package ai.deepdetect.services;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import ai.deepdetect.dto.event.ClassifyEvent;
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
            description = "this topic send classify event whenever the user requested with a new video"
        )
    )
    @KafkaAsyncOperationBinding
    public void sendClassifyEvent(ClassifyEvent classifyEvent) {
        CompletableFuture<SendResult<String, Object>> send = kafkaTemplate.send(topicName, classifyEvent);

        send.whenComplete((result, e) -> {
            if (e == null) 
                System.out.println("Send details : [" + classifyEvent + "]");

            else
                System.out.println("Error : " + e.getMessage());
            
        });
    }
}
