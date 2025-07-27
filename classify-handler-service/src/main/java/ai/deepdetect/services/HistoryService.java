package ai.deepdetect.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import ai.deepdetect.dto.event.ClassifyEvent;
import ai.deepdetect.dto.response.HistoryResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HistoryService {

    private final RestTemplate restTemplate;

    public void makeNewHistoryRecord(ClassifyEvent requestBody, String authToken) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);

        HttpEntity<ClassifyEvent> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<HistoryResponse> response = restTemplate.postForEntity(
                "http://localhost:8084/api/v1/history",
                requestEntity,
                HistoryResponse.class);

        if (response.getStatusCode() == HttpStatus.CREATED) 
            System.out.println("History record added successfully");

        else 
            System.out.println("Error: while add history record");
        

    }

}
