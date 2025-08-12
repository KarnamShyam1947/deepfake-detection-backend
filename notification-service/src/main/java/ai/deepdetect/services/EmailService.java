package ai.deepdetect.services;

import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import ai.deepdetect.clients.AuthenticationClient;
import ai.deepdetect.dto.UserResponse;
import ai.deepdetect.dto.event.NotificationEvent;
import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final AuthenticationClient authenticationClient;
    private final JavaMailSender javaMailSender;
    private final Configuration configuration;

    @Value("${application.frontend-url}")
    private String frontendUrl;

    @SuppressWarnings("null")
    public void sendEmail(NotificationEvent notificationEvent) {
        log.info("Sending result mail for request id : {}", notificationEvent.getRequestId());

        HashMap<String, Object> map = new HashMap<>();
        Writer out = new StringWriter();

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        UserResponse user = authenticationClient.getUserById(notificationEvent.getUserId()).getBody();

        try {
            helper.setFrom("vitap.library@gmail.com", "Admin");
            helper.setTo(user.getEmail());
            helper.setSubject("Your video analysis was completed.....!!!");

            Template template = configuration.getTemplate("result.ftl");
            Map<Object, Object> resultMetaData = getResultMetaData(notificationEvent);

            map.put("userName", user.getName());
            map.put("status", notificationEvent.getStatus());
            map.put("fileSize", notificationEvent.getSize());
            map.put("endDate", notificationEvent.getEndDate());
            map.put("fileName", notificationEvent.getFilename());
            map.put("duration", notificationEvent.getDuration());
            map.put("requestId", notificationEvent.getRequestId());
            map.put("startDate", notificationEvent.getStartDate());
            map.put("confidence", notificationEvent.getConfidence());
            map.put("originalFileUrl", notificationEvent.getVideoUrl());
            map.put("outputFileUrl", notificationEvent.getOutputVideoUrl());
            map.put("processingTime", "To be done");
            
            map.put("result", resultMetaData.get("result"));
            map.put("resultIcon", resultMetaData.get("resultIcon"));
            map.put("resultClass", resultMetaData.get("resultClass"));
            map.put("resultDescription", resultMetaData.get("resultDescription"));
            template.process(map, out);

            helper.setText(out.toString(), true);

            javaMailSender.send(mimeMessage);
            log.info("Successfully Send result mail for request id : {}", notificationEvent.getRequestId());
        } 
        catch (Exception e) {
            log.error(e.getMessage());
        }

    }

    private Map<Object, Object> getResultMetaData(NotificationEvent event) {
    
        if ("FAKE".equals(event.getResult())) {
            return Map.of(
                "result", "DEEPFAKE",
                "resultClass", "result-deepfake",
                "resultDescription", "The requested video was DEEPFAKE",
                "resultIcon", "<i class=\"fa-solid fa-triangle-exclamation\" style=\"color: red;\"></i>"
            );
        }
        
        else {
            return Map.of(
                "result", "AUTHENTIC",
                "resultClass", "result-authentic",
                "resultDescription", "The requested video was AUTHENTIC",
                "resultIcon", "<i class=\"fa-solid fa-check\" style=\"color: green;\"></i>"
            );
        }
    }
     
    public void sendActivationEmail(UserResponse userResponse) {
        log.info("Sending activation mail for user id : {}", userResponse.getId());

        HashMap<String, Object> map = new HashMap<>();
        Writer out = new StringWriter();

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        try {
            helper.setFrom("vitap.library@gmail.com", "Admin");
            helper.setTo(userResponse.getEmail());
            helper.setSubject("User Account Activation.....!!!");

            Template template = configuration.getTemplate("activate.ftl");

            map.put("userName", userResponse.getName());
            map.put(
                "activationLink", 
                String.format("%s/user/activate/%s", frontendUrl, userResponse.getToken())
            );

            template.process(map, out);

            helper.setText(out.toString(), true);

            javaMailSender.send(mimeMessage);
        log.info("successfully send activation mail for user id : {}", userResponse.getId());
        } 
        catch (Exception e) {
            log.error(e.getMessage());
        }

    }    
}
