package de.springframework.monitoring.hystrix.consumer;

import de.springframework.monitoring.hystrix.consumer.configuration.ProducerProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

// Naming überarbeiten
// Readme mit allen Links schreiben
// prometheus und grafana adden
// docker files schreiben
// key cloak import files anpssen
// extended hytrix example
// Profile für docker Build

@EnableCircuitBreaker
@EnableDiscoveryClient
@SpringBootApplication
@EnableConfigurationProperties({ ProducerProperties.class })
public class HystrixConsumerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HystrixConsumerServiceApplication.class, args);
    }

}
