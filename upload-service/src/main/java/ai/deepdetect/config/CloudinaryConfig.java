package ai.deepdetect.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;

@Configuration
public class CloudinaryConfig {
    
    @Value("${cloudinary.api-key}")
    public String apiKey;

    @Value("${cloudinary.api-secret}")
    public String apiSecret;

    @Value("${cloudinary.cloud-name}")
    public String cloudName;

    @Bean
    public Cloudinary getCloudinary() {
        String url = String.format("cloudinary://%s:%s@%s", apiKey, apiSecret, cloudName);
        Cloudinary cloudinary = new Cloudinary(url);
        System.out.println(url);

        return cloudinary;
    }

}
