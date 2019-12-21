package de.springframework.keycloak.credentials.server.web.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class MessageRestController {

    @GetMapping("/principal")
    public String getMessage(Authentication authentication) {
        log.info("Incoming call: {}.", authentication.getName());
        return "The message of the day is boring for user: " + authentication.getName();
    }

}
