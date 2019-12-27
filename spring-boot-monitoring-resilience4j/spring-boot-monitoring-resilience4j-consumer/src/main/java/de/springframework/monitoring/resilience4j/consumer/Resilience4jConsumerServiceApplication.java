package de.springframework.monitoring.resilience4j.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Resilience4jConsumerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(Resilience4jConsumerServiceApplication.class, args);
    }

}
