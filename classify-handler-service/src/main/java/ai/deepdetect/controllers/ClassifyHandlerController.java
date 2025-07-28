package ai.deepdetect.controllers;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.deepdetect.dto.ClassifyRequest;
import ai.deepdetect.dto.ClassifyRequestOld;
import ai.deepdetect.dto.event.ClassifyEvent;
import ai.deepdetect.services.AuthService;
import ai.deepdetect.services.HistoryService;
import ai.deepdetect.services.KafkaProducerService;
import ai.deepdetect.services.UploadService;
import ai.deepdetect.utils.RequestUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/classify")
public class ClassifyHandlerController {

    private final AuthService authService;
    private final UploadService uploadService;
    private final HistoryService historyService;
    private final KafkaProducerService kafkaProducerService;

    @PostMapping
    public ResponseEntity<?> handlePost(@RequestBody ClassifyRequest classifyRequest, HttpServletRequest httpServletRequest) {

        String authorizationHeader = RequestUtils.extractAuthorizationHeader(httpServletRequest);
        String requestId = UUID.randomUUID().toString();
        
        ClassifyEvent classifyEvent = ClassifyEvent
                                        .builder()
                                        .requestId(requestId)
                                        .startDate(new Date())
                                        .authToken(authorizationHeader)
                                        .videoUrl(classifyRequest.getUrl())
                                        .userId(authService.getCurrentUser().getId())
                                        .build();

        //1. call to history service
        historyService.makeNewHistoryRecord(classifyEvent, authorizationHeader);

        //2. Push to kafka
        kafkaProducerService.sendClassifyEvent(classifyEvent);

        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(Map.of(
                    "message", "Your classification request submitted successfully, your request id is : " + requestId
                ));
        
    }

    
    public void classifyHandlerOld(ClassifyRequestOld classifyRequest, HttpServletRequest httpServletRequest) throws IOException {
        String authToken = RequestUtils.extractAuthorizationHeader(httpServletRequest);
        String uploadFile = uploadService.uploadFile(classifyRequest.getVideo(), authToken);
        System.out.println(uploadFile);
    }
}
