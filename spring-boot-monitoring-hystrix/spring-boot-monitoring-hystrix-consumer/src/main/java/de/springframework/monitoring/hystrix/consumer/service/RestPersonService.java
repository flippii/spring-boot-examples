package de.springframework.monitoring.hystrix.consumer.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import de.springframework.monitoring.hystrix.consumer.client.PersonClient;
import de.springframework.monitoring.hystrix.consumer.service.dto.PersonDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestPersonService {

    private final PersonClient personClient;

    @HystrixCommand(
            fallbackMethod = "defaultPersons",
            groupKey = "RestTemplatePersonService",
            commandKey = "RestTemplatePersonService#getPersons"
    )
    public List<PersonDto> getPersons() {
        log.info("Request to get all Persons.");
        return personClient.getPersons();
    }

    private List<PersonDto> defaultPersons() {
        log.info("Fallback: get empty persons list.");
        return Collections.singletonList(getAnonymousPerson());
    }

    @HystrixCommand(
            fallbackMethod = "defaultPersonById",
            groupKey = "RestTemplatePersonService",
            commandKey = "RestTemplatePersonService#getPersonById"
    )
    public PersonDto getPersonById(Long id) {
        log.info("Request to get Person: {}.", id);
        return personClient.getPersonById(id);
    }

    private PersonDto defaultPersonById(Long id) {
        log.info("Fallback: get ANONYMOUS person with id: {}.", id);
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
