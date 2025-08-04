package ai.deepdetect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ClassifyHandlerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClassifyHandlerServiceApplication.class, args);
	}

}
