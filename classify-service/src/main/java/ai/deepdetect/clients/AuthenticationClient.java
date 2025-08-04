package ai.deepdetect.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ai.deepdetect.dto.UserResponse;

@FeignClient(name = "AUTHENTICATION-SERVICE")
public interface AuthenticationClient {
 
    @GetMapping("/api/v1/auth/user-entity")
    public ResponseEntity<UserResponse> getUserEntityByEmail(@RequestParam(required = true) String email); 
    
}
