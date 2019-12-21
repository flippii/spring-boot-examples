package de.springframework.keycloak.credentials.server;

import de.springframework.keycloak.credentials.server.configuration.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@SpringBootApplication
@EnableConfigurationProperties({ ApplicationProperties.class })
public class ClientCredentialsServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientCredentialsServerApplication.class, args);
    }

}
