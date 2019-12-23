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

    @HystrixCommand(commandKey = "allPersonsFromDB", fallbackMethod = "getCachedAllPersons")
    public List<PersonDto> getAllPersons() {
        log.info("Get all UserInfo");

        List<Person> persons = personJpaRepository.findAll();

        setCachedPersons(persons);

        return personMapper.map(persons);
    }

    protected List<PersonDto> getCachedAllPersons() {
        log.info("Retrieving all UserInfo from Redis");
        return personMapper.map(cachePersonRepository.findAll());
    }

    private void setCachedPersons(List<Person> persons) {
        if (cachePersonRepository.isEmpty() && !persons.isEmpty()) {
            log.info("UserInfoList not in Redis, insert it...");
            persons.forEach(cachePersonRepository::save);
        }
    }

    @HystrixCommand(commandKey = "personByIdFromDB", fallbackMethod = "getCachedPerson")
    public Optional<PersonDto> getPersonById(Long id) {
        log.info("Get UserInfo by id {}", id);

        Optional<Person> person = personJpaRepository.findById(id);

        person.ifPresent(this::setCachedUserInfoList);

        return person.map(personMapper::map);
    }

    protected Optional<PersonDto> getCachedPerson(Long id) {
        log.info("Retrieving UserInfo by id {} from Redis", id);

        return cachePersonRepository.findById(id.toString())
                .map(personMapper::map);
    }

    private void setCachedUserInfoList(Person person) {
        if (!cachePersonRepository.findById(person.getId().toString()).isPresent()) {
            log.info("UserInfo not in Redis, insert it...");
            cachePersonRepository.save(person);
        }
    }

}
