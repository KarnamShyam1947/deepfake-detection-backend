package ai.deepdetect.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import ai.deepdetect.dto.ExternalAPIResponse;
import ai.deepdetect.dto.event.ClassifyEvent;
import ai.deepdetect.services.ExternalAPIService;
import ai.deepdetect.services.HistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClassifyConsumer {

    private final ExternalAPIService externalAPIService;
    private final HistoryService historyService;

    @KafkaListener(topics = "classification-request-topic", groupId = "classification-request-group")
    public void consume(ClassifyEvent classifyEvent) {
        try{
            System.out.println("Consumer consumed : " + classifyEvent.toString());

            // REST CALL : Call the python api for actual prediction 
            ExternalAPIResponse apiResponse = externalAPIService.classifyVideo(classifyEvent);

            // REST CALL : update the history with result
            historyService.updateHistoryRecord(apiResponse, classifyEvent.getAuthToken());

            // // produce event to notification topic

            log.info("Classification Completed");
        }
        catch(Exception e) {
            System.out.println(e.toString());
        }
    }
}
