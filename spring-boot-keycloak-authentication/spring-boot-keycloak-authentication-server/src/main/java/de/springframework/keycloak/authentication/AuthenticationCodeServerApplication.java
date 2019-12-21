package de.springframework.keycloak.authentication;

import de.springframework.keycloak.authentication.configuration.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ ApplicationProperties.class })
public class AuthenticationCodeServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthenticationCodeServerApplication.class, args);
	}

}
