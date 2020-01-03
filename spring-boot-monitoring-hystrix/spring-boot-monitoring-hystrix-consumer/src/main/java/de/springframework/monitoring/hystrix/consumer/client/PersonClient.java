package de.springframework.monitoring.hystrix.consumer.client;

import de.springframework.monitoring.hystrix.consumer.service.dto.PersonDto;

import java.util.List;

public interface PersonClient {

    List<PersonDto> getPersons();

    PersonDto getPersonById(Long id);

}
