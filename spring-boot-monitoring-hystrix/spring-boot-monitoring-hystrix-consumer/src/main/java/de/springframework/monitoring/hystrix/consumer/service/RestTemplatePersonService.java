package de.springframework.monitoring.hystrix.consumer.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import de.springframework.monitoring.hystrix.consumer.configuration.ProducerProperties;
import de.springframework.monitoring.hystrix.consumer.service.dto.PersonDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

import static org.springframework.http.HttpMethod.GET;

@Slf4j
@Service
public class RestTemplatePersonService {

    private final RestTemplate restTemplate;
    private final ProducerProperties producerProperties;

    public RestTemplatePersonService(RestTemplateBuilder restTemplateBuilder, ProducerProperties producerProperties) {
        this.restTemplate = restTemplateBuilder.build();
        this.producerProperties = producerProperties;
    }

    @HystrixCommand(fallbackMethod = "defaultPersons")
    public List<PersonDto> getPersons() {
        UriComponents uri = createUriBuilder()
                .path("/persons")
                .build();

        return restTemplate.exchange(uri.toUri(), GET, null, new ParameterizedTypeReference<List<PersonDto>>() {})
                .getBody();
    }

    protected List<PersonDto> defaultPersons() {
        log.info("Fallback: get empty persons list.");
        return Collections.emptyList();
    }

    @HystrixCommand(fallbackMethod = "defaultPersonById")
    public PersonDto getPersonById(Long id) {
        UriComponents uri = createUriBuilder()
                .path("/persons/{id}")
                .buildAndExpand(id);

        return restTemplate.getForObject(uri.toUri(), PersonDto.class);
    }

    protected PersonDto defaultPersonById(Long id) {
        log.warn("Fallback: get ANONYMOUS person with id: {}.", id);

        return PersonDto.builder()
                .id(id)
                .firstName("ANONYMOUS")
                .build();
    }

    private UriComponentsBuilder createUriBuilder() {
        return UriComponentsBuilder.fromUriString(producerProperties.getUrl() + producerProperties.getPath());
    }

}
