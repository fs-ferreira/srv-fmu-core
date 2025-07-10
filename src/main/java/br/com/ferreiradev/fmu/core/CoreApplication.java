package br.com.ferreiradev.fmu.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class CoreApplication {

	@GetMapping("/health")
	public String HealthCheck(){
		return "up";
	}

	public static void main(String[] args) {
		SpringApplication.run(CoreApplication.class, args);
	}

}
