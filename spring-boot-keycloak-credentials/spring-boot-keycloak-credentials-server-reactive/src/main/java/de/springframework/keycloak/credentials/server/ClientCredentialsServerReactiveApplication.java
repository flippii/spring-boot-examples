package de.springframework.keycloak.credentials.server;

import de.springframework.keycloak.credentials.server.configuration.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;

@EnableWebFluxSecurity
@SpringBootApplication(exclude = {

})
@EnableConfigurationProperties({ ApplicationProperties.class })
public class ClientCredentialsServerReactiveApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientCredentialsServerReactiveApplication.class, args);
    }

}
