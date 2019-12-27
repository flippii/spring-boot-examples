package de.springframework.monitoring.resilience4j.consumer.service;

import de.springframework.monitoring.resilience4j.consumer.service.dto.PersonDto;

import java.util.List;

public interface PersonService {

    List<PersonDto> getPersons();

    PersonDto getPersonById(Long id);

}
