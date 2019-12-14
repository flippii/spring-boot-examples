package de.springframework.consul.client.web.rest;

import de.springframework.consul.client.configuration.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ExternalConfigurationResource {

    private final ApplicationProperties applicationProperties;

    @GetMapping("/configValue")
    public String configValue(){
        return applicationProperties.getMsg();
    }

}
