package de.springframework.keycloak.customers;

import de.springframework.keycloak.core.configuration.EnableCoreModule;
import de.springframework.keycloak.customers.configuration.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(exclude = {
        SecurityAutoConfiguration.class,
        UserDetailsServiceAutoConfiguration.class
})
@EnableSwagger2
@EnableCoreModule
@EnableConfigurationProperties({ ApplicationProperties.class })
public class KeycloakCustomersApplication {

    public static void main(String[] args) {
        SpringApplication.run(KeycloakCustomersApplication.class, args);
    }

}
