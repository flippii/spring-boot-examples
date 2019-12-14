package de.springframework.consul.client.web.rest;

import de.springframework.consul.client.services.DiscoveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ServiceDiscoveryResource {

    private final DiscoveryService discoveryService;

    @GetMapping("/instances")
    public List<URI> instanceURLs(){
        return discoveryService.getInstances();
    }

}
