package de.springframework.monitoring.resilience4j.consumer.client;

import de.springframework.monitoring.resilience4j.consumer.service.dto.PersonDto;

import java.util.List;

public interface PersonClient {

    List<PersonDto> getPersons();

    PersonDto getPersonById(Long id);

}
