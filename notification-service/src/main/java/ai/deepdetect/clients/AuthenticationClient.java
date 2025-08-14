package ai.deepdetect.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ai.deepdetect.config.custom.CustomFeignErrorDecoder;
import ai.deepdetect.dto.UserResponse;

@FeignClient(name = "AUTHENTICATION-SERVICE", configuration = CustomFeignErrorDecoder.class)
public interface AuthenticationClient {
 
    @GetMapping("/api/v1/auth/user-entity")
    public ResponseEntity<UserResponse> getUserEntityByEmail(@RequestParam(required = true) String email); 
    
    @GetMapping("/api/v1/auth/user-id")
    public ResponseEntity<UserResponse> getUserById(@RequestParam(required = true) int id);
}
