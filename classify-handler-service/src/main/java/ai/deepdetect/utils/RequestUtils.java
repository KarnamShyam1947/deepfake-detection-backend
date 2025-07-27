package ai.deepdetect.utils;

import org.springframework.http.HttpHeaders;

import jakarta.servlet.http.HttpServletRequest;

public class RequestUtils {
    
    public static String extractAuthorizationHeader(HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        return authHeader.substring(7);
    }

}
