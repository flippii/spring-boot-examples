package de.springframework.monitoring.hystrix.producer.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import de.springframework.monitoring.hystrix.producer.entity.Person;
import de.springframework.monitoring.hystrix.producer.repository.CachePersonRepository;
import de.springframework.monitoring.hystrix.producer.repository.PersonJpaRepository;
import de.springframework.monitoring.hystrix.producer.service.dto.PersonDto;
import de.springframework.monitoring.hystrix.producer.service.mapper.PersonMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonJpaRepository personJpaRepository;
    private final CachePersonRepository cachePersonRepository;
    private final PersonMapper personMapper;

    @HystrixCommand(
            groupKey = "PersonService",
            commandKey = "PersonService#getAllPersons",
            fallbackMethod = "getCachedPersons"
    )
    public List<PersonDto> getPersons() {
        log.info("Request to get all Persons.");

        List<Person> persons = personJpaRepository.findAll();

        setCachedPersons(persons);

        return personMapper.map(persons);
    }

    private List<PersonDto> getCachedPersons() {
        log.info("Request to get all Persons from Redis");
        return personMapper.map(cachePersonRepository.findAll());
    }

    private void setCachedPersons(List<Person> persons) {
        if (cachePersonRepository.isEmpty() && !persons.isEmpty()) {
            log.info("Persons not in Redis, insert it.");
            persons.forEach(cachePersonRepository::save);
        }
    }

    @HystrixCommand(
            groupKey = "PersonService",
            commandKey = "PersonService#getPersonById",
            fallbackMethod = "getCachedPerson"
    )
    public Optional<PersonDto> getPersonById(Long id) {
        log.info("Request to get Person: {}.", id);

        Optional<Person> person = personJpaRepository.findById(id);

        person.ifPresent(this::setCachedUserInfoList);

        return person.map(personMapper::map);
    }

    private Optional<PersonDto> getCachedPerson(Long id) {
        log.info("Retrieving Person: {} from Redis.", id);

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
