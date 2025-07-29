package ai.deepdetect.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import ai.deepdetect.dto.ExternalAPIResponse;
import ai.deepdetect.dto.HistoryResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HistoryService {

    private final RestTemplate restTemplate;

    public HistoryResponse updateHistoryRecord(ExternalAPIResponse apiResponse, String authToken) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);

        HttpEntity<ExternalAPIResponse> requestEntity = new HttpEntity<>(apiResponse, headers);

        ResponseEntity<HistoryResponse> response = restTemplate.exchange(
            "http://localhost:8084/api/v1/history",
            HttpMethod.PUT,
            requestEntity,
            HistoryResponse.class
        );

        if (response.getStatusCode() == HttpStatus.OK) 
            System.out.println("History record updated successfully");

        else 
            System.out.println("Error: while update history record");
        
        return response.getBody();
    }
}
