package de.springframework.monitoring.hystrix.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableCircuitBreaker
@EnableDiscoveryClient
@SpringBootApplication
public class HystrixProducerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HystrixProducerServiceApplication.class, args);
    }

}
