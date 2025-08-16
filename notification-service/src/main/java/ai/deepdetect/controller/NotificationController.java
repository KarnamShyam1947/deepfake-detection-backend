package ai.deepdetect.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.deepdetect.dto.event.NotificationEvent;
import ai.deepdetect.dto.event.UserEvent;
import ai.deepdetect.services.EmailService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notify")
public class NotificationController {

    private final EmailService emailService;

    @PostMapping("/notify")
    public ResponseEntity<Map<String, String>> sendNotification(@RequestBody NotificationEvent event) {
        emailService.sendEmail(event);

        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(Map.of(
                    "message", "email send to the user"
                ));
    }
    
    @PostMapping("/activation")
    public ResponseEntity<Map<String, String>> sendNotification(@RequestBody UserEvent event) {
        emailService.sendActivationEmail(event);

        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(Map.of(
                    "message", "email send to the user"
                ));
    }
    
}
