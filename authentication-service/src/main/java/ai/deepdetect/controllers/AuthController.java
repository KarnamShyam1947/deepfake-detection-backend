package ai.deepdetect.controllers;

import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.deepdetect.config.security.custom.CustomUserDetails;
import ai.deepdetect.dto.request.LoginRequest;
import ai.deepdetect.dto.request.RegisterRequest;
import ai.deepdetect.dto.response.LoginResponse;
import ai.deepdetect.dto.response.RegisterResponse;
import ai.deepdetect.entities.UserEntity;
import ai.deepdetect.services.AuthService;
import ai.deepdetect.services.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
// TODO: proper API Response
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;
 
    @PostMapping("/register")
    public Object register(@Valid @RequestBody RegisterRequest registerRequest) {
        UserEntity newUser = authService.registerUser(registerRequest);

        if (newUser == null) 
            return Map.of("error", "Error: Email already registered");
        
        RegisterResponse registerResponse = new RegisterResponse();
        BeanUtils.copyProperties(newUser, registerResponse);
        registerResponse.setMessage("User registered successfully");

        return registerResponse; 
    }

    @PostMapping("/login")
    public Object login(@Valid @RequestBody LoginRequest loginRequest) {
        UserEntity loginUser = authService.loginUser(loginRequest);
        String jwtToken = jwtService.generateJwtToken(new CustomUserDetails(loginUser));

        LoginResponse loginResponse = new LoginResponse();
        BeanUtils.copyProperties(loginUser, loginResponse);
        loginResponse.setJwtToken(jwtToken);
        loginResponse.setMessage("User logged in successfully");

        return loginResponse;
    }
    
    
}
