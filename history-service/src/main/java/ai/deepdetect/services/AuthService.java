package ai.deepdetect.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

}
