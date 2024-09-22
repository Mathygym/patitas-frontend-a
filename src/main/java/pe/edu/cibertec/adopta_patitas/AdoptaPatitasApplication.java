package pe.edu.cibertec.adopta_patitas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@SpringBootApplication
public class AdoptaPatitasApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdoptaPatitasApplication.class, args);
	}

	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("http://localhost:8081") //
				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
	}

}
