package nl.novi.automate;

import nl.novi.automate.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Automate {

	public static void main(String[] args) {
		SpringApplication.run(Automate.class, args);
	}

	// test hieronder voor aanmaken systemUser
	@Bean
	public CommandLineRunner initSystemUser(UserService userService) {
		return (args) -> userService.createSystemUserIfNotExists();
	}

}
