package de.springframework.monitoring.resilience4j.consumer;

import de.springframework.monitoring.resilience4j.consumer.configuration.ProducerProperties;
import de.springframework.monitoring.resilience4j.consumer.configuration.RestTemplateProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
@EnableConfigurationProperties({ ProducerProperties.class, RestTemplateProperties.class})
public class Resilience4jConsumerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(Resilience4jConsumerServiceApplication.class, args);
    }

}
