package ai.deepdetect.config.security.oauth;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import ai.deepdetect.dto.response.LoginResponse;
import ai.deepdetect.entities.UserEntity;
import ai.deepdetect.services.JwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomOAuthSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;

    @Value("${application.frontend-url}")
    private String frontendUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        Object principal = authentication.getPrincipal();
        UserEntity userEntity = ((CustomOAuthUser) principal).getUserEntity();

        String jwtToken = jwtService.generateJwtToken(userEntity.getEmail());
        LoginResponse loginResponse = new LoginResponse();
        BeanUtils.copyProperties(userEntity, loginResponse);
        loginResponse.setJwtToken(jwtToken);
        loginResponse.setMessage("OAuth Login Successful");

        String userJson = new ObjectMapper().writeValueAsString(loginResponse);
        String encodedUser = URLEncoder.encode(userJson, StandardCharsets.UTF_8.toString());
        response.sendRedirect(frontendUrl + "?token=" + encodedUser);
    }

}
