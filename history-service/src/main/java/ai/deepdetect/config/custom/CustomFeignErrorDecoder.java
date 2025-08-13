package ai.deepdetect.config.custom;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomFeignErrorDecoder implements ErrorDecoder {
    
    @Override
    public Exception decode(String methodKey, Response response) {
        HttpStatus status = HttpStatus.valueOf(response.status());
        String responseBody = extractResponseBody(response);

        // Log error details
        log.error("Feign client error. Method: {}, Status: {}, Body: {}", 
                     methodKey, status, responseBody);

        // Map status codes to exceptions
        switch (status) {
            case BAD_REQUEST:
                return new IllegalArgumentException("Invalid request: " + responseBody);

            case UNAUTHORIZED:
                return new SecurityException("Unauthorized access");

            case FORBIDDEN:
                return new ResponseStatusException(HttpStatus.FORBIDDEN, "Access forbidden");

            case NOT_FOUND:
                return new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find for the request resource");

            case INTERNAL_SERVER_ERROR:
                return new RuntimeException("Internal server error");

            case SERVICE_UNAVAILABLE:
                return new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "The requested service is unavailable. Please try again after sometime");
                
            default:
                return new Exception("Unexpected error: " + responseBody);
        }
    }

    private String extractResponseBody(Response response) {
        if (response.body() == null) {
            return "No response body";
        }

        try {
            return new String(response.body().asInputStream().readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException ex) {
            log.error("Failed to read response body", ex);
            return "Error reading response body";
        }
    }
}