package de.springframework.consul.client.services;

import de.springframework.consul.client.handler.RestTemplateResponseErrorHandler;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.naming.ServiceUnavailableException;
import java.net.URI;

@Service
public class RestClientService {

    private final RestTemplate restTemplate;
    private final DiscoveryService discoveryService;

    public RestClientService(RestTemplateBuilder restTemplateBuilder, DiscoveryService discoveryService) {
        this.restTemplate = restTemplateBuilder
                .errorHandler(new RestTemplateResponseErrorHandler())
                .build();
        this.discoveryService = discoveryService;
    }

    public String callServiceWithRest() throws RestClientException, ServiceUnavailableException {
        URI service = discoveryService.getFirstInstance()
                .map(uri -> uri.resolve("/ping"))
                .orElseThrow(ServiceUnavailableException::new);

        return restTemplate.getForEntity(service, String.class)
                .getBody();
    }

}
