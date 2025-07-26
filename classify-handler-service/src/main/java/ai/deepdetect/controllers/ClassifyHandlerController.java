package ai.deepdetect.controllers;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.deepdetect.dto.ClassifyRequest;
import ai.deepdetect.dto.ClassifyRequestOld;
import ai.deepdetect.dto.event.ClassifyEvent;
import ai.deepdetect.services.AuthService;
import ai.deepdetect.services.UploadService;
import ai.deepdetect.utils.RequestUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/classify")
public class ClassifyHandlerController {

    private final UploadService uploadService;
    private final AuthService authService;
    
    @GetMapping
    public String get() {
        return "Hello, World";
    }

    @PostMapping
    public void handlePost(@RequestBody ClassifyRequest classifyRequest) {
        
        ClassifyEvent classifyEvent = ClassifyEvent
                                        .builder()
                                        .startDate(new Date())
                                        .videoUrl(classifyRequest.getUrl())
                                        .requestId(UUID.randomUUID().toString())
                                        .userId(authService.getCurrentUser().getId())
                                        .build();
        //1. Push to kafka
        //2. call to history service
    }

    
    public void classifyHandlerOld(ClassifyRequestOld classifyRequest, HttpServletRequest httpServletRequest) throws IOException {
        String authToken = RequestUtils.extractAuthorizationHeader(httpServletRequest);
        String uploadFile = uploadService.uploadFile(classifyRequest.getVideo(), authToken);
        System.out.println(uploadFile);
    }
}
