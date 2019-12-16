package de.springframework.keycloak.customers;

import de.springframework.keycloak.customers.configuration.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ ApplicationProperties.class })
public class KeycloakCustomersApplication {

    public static void main(String[] args) {
        SpringApplication.run(KeycloakCustomersApplication.class, args);
    }

}
