package ai.deepdetect.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import ai.deepdetect.dto.event.ClassifyEvent;
import ai.deepdetect.dto.response.HistoryResponse;

@FeignClient(name = "HISTORY-SERVICE")
public interface HistoryClient {

    @PostMapping(
        path = "/api/v1/history",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HistoryResponse> handleNewRequest(
        @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
        @RequestBody ClassifyEvent request
    );
    
}
