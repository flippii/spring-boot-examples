package de.springframework.monitoring.hystrix.producer.service.mapper;

import de.springframework.monitoring.hystrix.producer.entity.Person;
import de.springframework.monitoring.hystrix.producer.service.dto.PersonDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PersonMapper {

    public List<PersonDto> map(List<Person> persons) {
        return persons.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    public PersonDto map(Person person) {
        return PersonDto.builder()
                .id(person.getId())
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .build();
    }

}
