package ai.deepdetect.clients.fallbacks;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import ai.deepdetect.clients.AuthenticationClient;
import ai.deepdetect.dto.UserResponse;

@Component
public class AuthenticationClientFallBack implements AuthenticationClient {

    @Override
    public ResponseEntity<UserResponse> getUserEntityByEmail(String email) {
        System.out.println("\n\nFallback called\n\n");
        UserResponse userResponse = UserResponse.builder()
                                    .id(0)
                                    .role("UNKNOWN")
                                    .name("UNKNOWN")
                                    .email("UNKNOWN")
                                    .build();

        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE.value())
                .body(userResponse);
    }
    
}
