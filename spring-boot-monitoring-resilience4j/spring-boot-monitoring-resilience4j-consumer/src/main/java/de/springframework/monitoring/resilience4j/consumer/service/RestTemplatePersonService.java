package de.springframework.monitoring.resilience4j.consumer.service;

import de.springframework.monitoring.resilience4j.consumer.configuration.ProducerProperties;
import de.springframework.monitoring.resilience4j.consumer.service.dto.PersonDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;

import static de.springframework.monitoring.resilience4j.consumer.service.Recilience4jConstants.PERSON_CLIENT;
import static org.springframework.http.HttpMethod.GET;

@Slf4j
@Service
@RateLimiter(name = PERSON_CLIENT)
@Retry(name = PERSON_CLIENT)
public class RestTemplatePersonService implements PersonService {

    private final RestTemplate restTemplate;
    private final ProducerProperties producerProperties;

    public RestTemplatePersonService(RestTemplateBuilder restTemplateBuilder, ProducerProperties producerProperties) {
        this.restTemplate = restTemplateBuilder.build();
        this.producerProperties = producerProperties;
    }

    @Override
    @CircuitBreaker(name = PERSON_CLIENT, fallbackMethod = "defaultPersons")
    public List<PersonDto> getPersons() {
        UriComponents uri = createUriBuilder()
                .path("/persons")
                .build();

        return restTemplate.exchange(uri.toUri(), GET, null, new ParameterizedTypeReference<List<PersonDto>>() {})
                .getBody();
    }

    private List<PersonDto> defaultPersons(RestClientException ex) {
        log.info("Fallback: get empty persons list.", ex);
        return Collections.emptyList();
    }

    @Override
    @CircuitBreaker(name = PERSON_CLIENT, fallbackMethod = "defaultPersonById")
    public PersonDto getPersonById(Long id) {
        UriComponents uri = createUriBuilder()
                .path("/persons/{id}")
                .buildAndExpand(id);

        return restTemplate.getForObject(uri.toUri(), PersonDto.class);
    }

    private PersonDto defaultPersonById(Long id, RestClientException ex) {
        log.warn("Fallback: get ANONYMOUS person with id: {}.", id, ex);

        return PersonDto.builder()
                .id(id)
                .firstName("ANONYMOUS")
                .build();
    }

    private UriComponentsBuilder createUriBuilder() {
        return UriComponentsBuilder.fromUriString(producerProperties.getUrl() + producerProperties.getPath());
    }

}
