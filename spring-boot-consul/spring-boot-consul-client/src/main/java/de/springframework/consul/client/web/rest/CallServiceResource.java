package de.springframework.consul.client.web.rest;

import de.springframework.consul.client.services.RestClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import javax.naming.ServiceUnavailableException;

@RestController
@RequiredArgsConstructor
public class CallServiceResource {

    private final RestClientService restClientService;

    @GetMapping("/call")
    public String discoveryPing() throws RestClientException, ServiceUnavailableException {
        return restClientService.callServiceWithRest();
    }

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

}
