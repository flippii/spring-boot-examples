package de.springframework.monitoring.hystrix.consumer;

import de.springframework.monitoring.hystrix.consumer.configuration.FeignProperties;
import de.springframework.monitoring.hystrix.consumer.configuration.ProducerProperties;
import de.springframework.monitoring.hystrix.consumer.configuration.RestTemplateProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableCircuitBreaker
@EnableDiscoveryClient
@SpringBootApplication
@EnableConfigurationProperties({ ProducerProperties.class, RestTemplateProperties.class, FeignProperties.class })
@EnableFeignClients(basePackages = "de.springframework.monitoring.hystrix.consumer.service")
public class HystrixConsumerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HystrixConsumerServiceApplication.class, args);
    }

}
