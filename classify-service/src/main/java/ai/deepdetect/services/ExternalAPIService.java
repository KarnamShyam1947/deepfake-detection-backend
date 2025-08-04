package ai.deepdetect.services;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import ai.deepdetect.dto.ExternalAPIResponse;
import ai.deepdetect.dto.event.ClassifyEvent;

@Service
public class ExternalAPIService {
    
    public ExternalAPIResponse classifyVideo(ClassifyEvent event) {
        // TODO: Make an api to external python api

        // mocking call
        System.out.println("Making an external api call");
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        double randomConfidence = Math.random();

        ExternalAPIResponse response = new ExternalAPIResponse();
        BeanUtils.copyProperties(event, response);
        response.setConfidence(randomConfidence);
        response.setResult(randomConfidence > 0.5 ? "REAL" : "FAKE");
        response.setOutputVideoUrl("https://vedio.com/output.mp4");

        return response;
        
    }

}
