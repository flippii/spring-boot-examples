package de.springframework.monitoring.hystrix.consumer;

import de.springframework.monitoring.hystrix.consumer.configuration.ProducerProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableCircuitBreaker
@EnableDiscoveryClient
@SpringBootApplication
@EnableConfigurationProperties({ ProducerProperties.class })
public class HystrixConsumerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HystrixConsumerServiceApplication.class, args);
    }

}
