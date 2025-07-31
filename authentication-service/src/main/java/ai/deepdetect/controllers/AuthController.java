package ai.deepdetect.controllers;

import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.deepdetect.config.security.custom.CustomUserDetails;
import ai.deepdetect.dto.request.ForgotPasswordRequest;
import ai.deepdetect.dto.request.LoginRequest;
import ai.deepdetect.dto.request.RegisterRequest;
import ai.deepdetect.dto.request.SetPasswordRequest;
import ai.deepdetect.dto.response.LoginResponse;
import ai.deepdetect.dto.response.RegisterResponse;
import ai.deepdetect.dto.response.UserResponse;
import ai.deepdetect.entities.UserEntity;
import ai.deepdetect.exceptions.OTPExpiredException;
import ai.deepdetect.exceptions.UserAlreadyExistsException;
import ai.deepdetect.exceptions.UserNotFoundException;
import ai.deepdetect.services.AuthService;
import ai.deepdetect.services.JwtService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(
    name = "Authentication Controller",
    description = "All endpoint for authentication"
)
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;
 
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) throws UserAlreadyExistsException, UserNotFoundException {
        UserEntity newUser = authService.registerUser(registerRequest);

        if (newUser == null) 
            throw new UserAlreadyExistsException("user already exists with email : " + registerRequest.getEmail());
        
        RegisterResponse registerResponse = new RegisterResponse();
        BeanUtils.copyProperties(newUser, registerResponse);
        registerResponse.setMessage("User registered successfully");

        return ResponseEntity
                .status(HttpStatus.CREATED.value())
                .body(registerResponse); 
    }

    @PostMapping("/login")
    public Object login(@Valid @RequestBody LoginRequest loginRequest) {
        UserEntity loginUser = authService.loginUser(loginRequest);
        String jwtToken = jwtService.generateJwtToken(new CustomUserDetails(loginUser));

        LoginResponse loginResponse = new LoginResponse();
        BeanUtils.copyProperties(loginUser, loginResponse);
        loginResponse.setJwtToken(jwtToken);
        loginResponse.setMessage("User logged in successfully");

        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(loginResponse); 
    }
    
    @PostMapping("/forgot-password")
    public Object forgotPassword(@Valid @RequestBody ForgotPasswordRequest forgotPasswordRequest) throws UserNotFoundException {
        UserEntity user = authService.forgotPassword(forgotPasswordRequest);
        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(Map.of(
                    "message", "Password reset link was sent to your mail",
                    "user", UserResponse.entityToResponse(user)
                ));
    }
   
    @PostMapping("/set-password")
    public Object setPassword(@Valid @RequestBody SetPasswordRequest setPasswordRequest) throws UserNotFoundException, OTPExpiredException {
        UserEntity user = authService.setUserPassword(setPasswordRequest);
        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(Map.of(
                    "message", "Password reset successful",
                    "user", UserResponse.entityToResponse(user)
                ));
    }
    
    @GetMapping("/user")
    public ResponseEntity<UserResponse> getUserByEmail(
        @RequestParam(required = true) String email
    ) throws UserNotFoundException {
        UserEntity userByEmail = authService.getUserByEmail(email);
        UserResponse userResponse = UserResponse.entityToResponse(userByEmail);

        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(userResponse);
    }
    
    @GetMapping("/user-entity")
    public ResponseEntity<UserEntity> getUserEntityByEmail(
        @RequestParam(required = true) String email
    ) throws UserNotFoundException {
        UserEntity userByEmail = authService.getUserByEmail(email);

        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(userByEmail);
    }
    
}
