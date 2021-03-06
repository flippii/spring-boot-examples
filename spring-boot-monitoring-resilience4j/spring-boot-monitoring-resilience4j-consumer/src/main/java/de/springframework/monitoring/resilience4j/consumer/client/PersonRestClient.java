package de.springframework.monitoring.resilience4j.consumer.client;

import de.springframework.monitoring.resilience4j.consumer.configuration.ProducerProperties;
import de.springframework.monitoring.resilience4j.consumer.service.dto.PersonDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static org.springframework.http.HttpMethod.GET;

@Component
@RequiredArgsConstructor
public class PersonRestClient implements PersonClient {

    private final RestTemplate restTemplate;
    private final ProducerProperties producerProperties;

    @Override
    public List<PersonDto> getPersons() {
        UriComponents uri = createUriBuilder()
                .path("/persons")
                .build();

        return restTemplate.exchange(uri.toUri(), GET, null, new ParameterizedTypeReference<List<PersonDto>>() {})
                .getBody();
    }

    @Override
    public PersonDto getPersonById(Long id) {
        UriComponents uri = createUriBuilder()
                .path("/persons/{id}")
                .buildAndExpand(id);

        return restTemplate.getForObject(uri.toUri(), PersonDto.class);
    }

    private UriComponentsBuilder createUriBuilder() {
        return UriComponentsBuilder.fromUriString(producerProperties.getUrl() + producerProperties.getPath());
    }

}
