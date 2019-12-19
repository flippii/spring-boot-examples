package de.springframework.keycloak.credentials.client.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class CallServerRestController {

    @Value("#{ @environment['example.baseUrl'] }")
    private String serverBaseUrl;

    private final RestTemplate restTemplate;

    @GetMapping
    public ResponseEntity<String> getMessage() {
        return Optional.ofNullable(restTemplate.getForObject(serverBaseUrl + "/mod", String.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

}
