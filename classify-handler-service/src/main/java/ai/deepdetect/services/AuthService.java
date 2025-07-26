package ai.deepdetect.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import ai.deepdetect.config.custom.CustomUserDetails;
import ai.deepdetect.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final RestTemplate restTemplate;

    public UserResponse getUser(String username) {
        String url = "http://localhost:8081/api/v1/auth/user-entity?email=" + username;
        UserResponse response = restTemplate.getForObject(url, UserResponse.class);
        return response;
    }

    public UserResponse getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var principal = authentication.getPrincipal();

        if (principal instanceof CustomUserDetails) {
            UserResponse userEntity = ((CustomUserDetails) principal).getUserEntity();
            return userEntity;

        } else {
            return null;
        }
    }

}
