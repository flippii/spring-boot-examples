package de.springframework.monitoring.resilience4j.producer.service;

import de.springframework.monitoring.resilience4j.producer.entity.Person;
import de.springframework.monitoring.resilience4j.producer.repository.CachePersonRepository;
import de.springframework.monitoring.resilience4j.producer.repository.PersonJpaRepository;
import de.springframework.monitoring.resilience4j.producer.service.dto.PersonDto;
import de.springframework.monitoring.resilience4j.producer.service.mapper.PersonMapper;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static de.springframework.monitoring.resilience4j.producer.service.Recilience4jConstants.PERSON_SERVICE;

@Slf4j
@Service
@RequiredArgsConstructor
@RateLimiter(name = PERSON_SERVICE)
@Retry(name = PERSON_SERVICE)
@Bulkhead(name = PERSON_SERVICE)
public class PersonService {

    private final PersonJpaRepository personJpaRepository;
    private final CachePersonRepository cachePersonRepository;
    private final PersonMapper personMapper;

    @Bulkhead(name = PERSON_SERVICE)
    @CircuitBreaker(name = PERSON_SERVICE, fallbackMethod = "getCachedPersons")
    public List<PersonDto> getPersons() {
        log.info("Request to get all Persons.");

        List<Person> persons = personJpaRepository.findAll();

        setCachedPersons(persons);

        return personMapper.map(persons);
    }

    private List<PersonDto> getCachedPersons(Throwable ex) {
        log.info("Request to get all Persons from Redis ({}).", ex.getMessage());
        return personMapper.map(cachePersonRepository.findAll());
    }

    private void setCachedPersons(List<Person> persons) {
        if (cachePersonRepository.isEmpty() && !persons.isEmpty()) {
            log.info("Persons not in Redis, insert it.");
            persons.forEach(cachePersonRepository::save);
        }
    }

    @Bulkhead(name = PERSON_SERVICE)
    @CircuitBreaker(name = PERSON_SERVICE, fallbackMethod = "getCachedPerson")
    public Optional<PersonDto> getPersonById(Long id) {
        log.info("Request to get Person: {}.", id);

        Optional<Person> person = personJpaRepository.findById(id);

        person.ifPresent(this::setCachedUserInfoList);

        return person.map(personMapper::map);
    }

    private Optional<PersonDto> getCachedPerson(Long id, Throwable ex) {
        log.info("Retrieving Person: {} from Redis ({}).", id, ex.getMessage());

        return cachePersonRepository.findById(id.toString())
                .map(personMapper::map);
    }

    private void setCachedUserInfoList(Person person) {
        if (!cachePersonRepository.findById(person.getId().toString()).isPresent()) {
            log.info("Person not in Redis, insert it.");
            cachePersonRepository.save(person);
        }
    }

}
