package de.springframework.monitoring.hystrix.consumer.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class RestTemplateConfiguration {

    private final RestTemplateProperties restTemplateProperties;

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .setConnectTimeout(restTemplateProperties.getConnectTimeout())
                .setReadTimeout(restTemplateProperties.getReadTimeout())
                .build();
    }

}
