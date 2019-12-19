package de.springframework.keycloak.credentials.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@EnableResourceServer
@SpringBootApplication(exclude = { UserDetailsServiceAutoConfiguration.class })
public class ClientCredentialsServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientCredentialsServerApplication.class, args);
    }

}
