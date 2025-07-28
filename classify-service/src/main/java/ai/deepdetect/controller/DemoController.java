package ai.deepdetect.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/classify-service")
public class DemoController {
    
    @GetMapping
    public String demo() {
        return "working...";
    }
}
