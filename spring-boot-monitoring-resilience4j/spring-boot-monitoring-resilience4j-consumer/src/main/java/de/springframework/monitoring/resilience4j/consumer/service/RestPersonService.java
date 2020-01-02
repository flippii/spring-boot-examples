package de.springframework.monitoring.resilience4j.consumer.service;

import de.springframework.monitoring.resilience4j.consumer.client.PersonClient;
import de.springframework.monitoring.resilience4j.consumer.service.dto.PersonDto;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static de.springframework.monitoring.resilience4j.consumer.service.Recilience4jConstants.PERSON_CLIENT;

@Slf4j
@Service
@RequiredArgsConstructor
@RateLimiter(name = PERSON_CLIENT)
@Retry(name = PERSON_CLIENT)
@Bulkhead(name = PERSON_CLIENT)
public class RestPersonService implements PersonService {

    private final PersonClient personClient;

    @Override
    @CircuitBreaker(name = PERSON_CLIENT, fallbackMethod = "defaultPersons")
    public List<PersonDto> getPersons() {
        return personClient.getPersons();
    }

    private List<PersonDto> defaultPersons(Throwable ex) {
        log.info("Fallback: get empty persons list ({}).", ex.getMessage());
        return Collections.singletonList(getAnonymousPerson());
    }

    @Override
    @CircuitBreaker(name = PERSON_CLIENT, fallbackMethod = "defaultPersonById")
    public PersonDto getPersonById(Long id) {
        return personClient.getPersonById(id);
    }

    private PersonDto defaultPersonById(Long id, Throwable ex) {
        log.warn("Fallback: get ANONYMOUS person with id: {} ({}).", id, ex.getMessage());
        return getAnonymousPerson();
    }

    private PersonDto getAnonymousPerson() {
        return PersonDto.builder()
                .id(0L)
                .firstName("ANONYMOUS")
                .lastName("no")
                .build();
    }

}
