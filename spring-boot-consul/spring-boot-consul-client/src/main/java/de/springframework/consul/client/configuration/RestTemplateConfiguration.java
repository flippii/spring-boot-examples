package de.springframework.consul.client.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Configuration
public class RestTemplateConfiguration {

    @Bean
    @Qualifier("customRestTemplateCustomizer")
    public CustomRestTemplateCustomizer customRestTemplateCustomizer() {
        return new CustomRestTemplateCustomizer();
    }

    @Bean
    @DependsOn(value = {"customRestTemplateCustomizer"})
    public RestTemplateBuilder restTemplateBuilder() {
        return new RestTemplateBuilder(customRestTemplateCustomizer());
    }

    private static class CustomRestTemplateCustomizer implements RestTemplateCustomizer {

        @Override
        public void customize(RestTemplate restTemplate) {
            restTemplate.getInterceptors().add(new CustomClientHttpRequestInterceptor());
        }

    }

    @Slf4j
    private static class CustomClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {

        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                                            ClientHttpRequestExecution execution) throws IOException {

            logRequestDetails(request);

            return execution.execute(request, body);
        }

        private void logRequestDetails(HttpRequest request) {
            log.info("Request Headers: {}", request.getHeaders());
            log.info("Request Method: {}", request.getMethod());
            log.info("Request URI: {}", request.getURI());
        }

    }

}
