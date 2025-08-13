package ai.deepdetect.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import ai.deepdetect.config.custom.CustomFeignErrorDecoder;
import ai.deepdetect.dto.ExternalAPIResponse;
import ai.deepdetect.dto.HistoryResponse;

@FeignClient(name = "HISTORY-SERVICE", configuration = CustomFeignErrorDecoder.class)
public interface HistoryClient {

    @PutMapping(
        path = "/api/v1/history",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HistoryResponse> handleUpdateRequest(
        @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
        @RequestBody ExternalAPIResponse request
    );
}
