package ai.deepdetect.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.deepdetect.config.security.custom.CustomUserDetails;
import ai.deepdetect.config.security.oauth.CustomOAuthUser;
import ai.deepdetect.dto.request.ChangePasswordRequest;
import ai.deepdetect.entities.UserEntity;
import ai.deepdetect.repositories.UserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/current-user")
@Tag(
    name = "Current User Controller",
    description = "All endpoint for current users"
)
public class CurrentUserController {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<?> get() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var principal = authentication.getPrincipal();
        UserEntity currentUser = null;

        if (principal instanceof CustomUserDetails)
            currentUser = ((CustomUserDetails) principal).getUserEntity();

        
        else if(principal instanceof CustomOAuthUser)
            currentUser = ((CustomOAuthUser) principal).getUserEntity();
        
        return ResponseEntity.ok(currentUser);
 
    }
    
    @PostMapping("/change-password")
    public Object changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var principal = authentication.getPrincipal();
        UserEntity userEntity = ((CustomUserDetails) principal).getUserEntity();

        if (passwordEncoder.matches(changePasswordRequest.getOldPassword(), userEntity.getPassword())) {
            userEntity.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
            userRepository.save(userEntity);

            return ResponseEntity
                    .ok()
                    .body(Map.of(
                        "message", "Password updated successfully"
                    ));
        }
        
        return ResponseEntity
                .badRequest()
                .body(Map.of(
                    "error", "Old Password was not matching"
                ));
 
    }

    
}
