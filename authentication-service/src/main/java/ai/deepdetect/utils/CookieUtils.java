package ai.deepdetect.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

public class CookieUtils {

@Value("${application.cookie.expiration}")
    private static int expiry;

    @Value("${application.cookie.refreshExpiration}")
    private static int refreshExpiry;
   
    public static Cookie generateCookie(
        int age,
        String path,
        String cookieKey,
        String cookieValue,
        boolean isHttpOnly
    ) {
        Cookie cookie = new Cookie(cookieKey, cookieValue);
        cookie.setHttpOnly(isHttpOnly);
        cookie.setMaxAge(age);
        cookie.setPath(path);
        cookie.setAttribute("SameSite", "None");
        
        return cookie;
    }
    
    public static Cookie generateCookie(String cookieKey,String cookieValue) {
        return generateCookie(expiry, "/",cookieKey, cookieValue, false);
    }
    
    public static Cookie generateCookie(String cookieKey,String cookieValue, boolean isHttpOnly) {
        return generateCookie(expiry, "/",cookieKey, cookieValue, isHttpOnly);
    }
    
    public static Cookie generateCookie(String cookieKey,String cookieValue, boolean isHttpOnly, int time) {
        return generateCookie(time, "/",cookieKey, cookieValue, isHttpOnly);
    }
    
    public static Cookie generateCookie(String cookieKey,String cookieValue, int time) {
        return generateCookie(time, "/",cookieKey, cookieValue, false);
    }

    public static String getCookieValue(HttpServletRequest httpRequest, String cookieKey) {
        Cookie[] cookies = httpRequest.getCookies();

        if (cookies == null) 
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "cookies not found with the request body"
            );
        
        String value = null;
        for (Cookie cookie : cookies) 
            if (cookieKey.equals(cookie.getName())) 
                value = cookie.getValue();

        return value;
    }

}