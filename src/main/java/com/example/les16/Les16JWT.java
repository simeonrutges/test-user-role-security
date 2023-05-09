package com.example.les16;

import com.example.les16.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Les16JWT {

	public static void main(String[] args) {
		SpringApplication.run(Les16JWT.class, args);
	}

	// test hieronder voor aanmaken systemUser
	@Bean
	public CommandLineRunner initSystemUser(UserService userService) {
		return (args) -> userService.createSystemUserIfNotExists();
	}

}
