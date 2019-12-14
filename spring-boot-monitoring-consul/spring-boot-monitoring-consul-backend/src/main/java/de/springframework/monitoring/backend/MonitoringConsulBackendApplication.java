package de.springframework.monitoring.backend;

import de.springframework.monitoring.backend.configuration.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ ApplicationProperties.class })
public class MonitoringConsulBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(MonitoringConsulBackendApplication.class, args);
    }

}
