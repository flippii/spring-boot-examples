package de.springframework.monitoring.resilience4j.producer.web.rest;

import de.springframework.monitoring.resilience4j.producer.service.PersonService;
import de.springframework.monitoring.resilience4j.producer.service.dto.PersonDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/persons")
public class PersonRestController {

    private final PersonService personService;

    @GetMapping
    public List<PersonDto> getPersons() {
        log.info("REST request to get all Persons.");
        return personService.getPersons();
    }

    @GetMapping("/{id}")
    public Optional<PersonDto> getPersonById(@PathVariable("id") Long id) {
        log.info("REST request to get Person: {}.", id);
        return personService.getPersonById(id);
    }

}
