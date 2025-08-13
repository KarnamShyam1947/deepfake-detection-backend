package ai.deepdetect.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ai.deepdetect.config.custom.CustomFeignErrorDecoder;
import feign.codec.ErrorDecoder;

@Configuration
public class FeignClientConfig {

    @Bean
    public ErrorDecoder customFeignErrorDecoder() {
        return new CustomFeignErrorDecoder();
    }
}
