package de.springframework.monitoring.resilience4j.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class Resilience4jProducerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(Resilience4jProducerServiceApplication.class, args);
    }

}
