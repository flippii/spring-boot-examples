package de.springframework.keycloak.credentials.server.web.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class MessageRestController {

    @GetMapping("/principal")
    public Mono<String> getMessage() {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .map(principal -> {
                    String name = (String) principal;
                    log.info("Incoming call: {}.", name);
                    return String.format("The message of the day is boring for user: %s", name);
                });
    }

}
