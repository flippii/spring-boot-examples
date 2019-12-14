package de.springframework.consul.client.services;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class DiscoveryService {

    private final DiscoveryClient discoveryClient;

    public List<URI> getInstances() {
        return instances()
                .collect(Collectors.toList());
    }

    public Optional<URI> getFirstInstance() {
        return instances()
                .findFirst();
    }

    private Stream<URI> instances() {
        return discoveryClient.getInstances("dummy-service")
                .stream()
                .map(ServiceInstance::getUri);
    }

}
