package ai.deepdetect.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.deepdetect.config.security.custom.CustomUserDetails;
import ai.deepdetect.dto.response.UserResponse;
import ai.deepdetect.entities.UserEntity;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/current-user")
public class CurrentUserController {

    @GetMapping
    public Object get() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var principal = authentication.getPrincipal();

        if (principal instanceof CustomUserDetails) {
            UserEntity userEntity = ((CustomUserDetails) principal).getUserEntity();
            return UserResponse.entityToResponse(userEntity);

        } else {
            return principal.toString();
        }
 
    }

    
}
