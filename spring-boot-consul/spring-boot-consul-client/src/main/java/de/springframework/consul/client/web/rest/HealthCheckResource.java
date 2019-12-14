package de.springframework.consul.client.web.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckResource {

    @GetMapping("/custom-health-check")
    public ResponseEntity<String> customHealthCheck() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/custom-health-check-forbidden")
    public ResponseEntity<String> customHealthCheckForbidden() {
        String message = "Testing my healh check function";
        return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
    }

}
