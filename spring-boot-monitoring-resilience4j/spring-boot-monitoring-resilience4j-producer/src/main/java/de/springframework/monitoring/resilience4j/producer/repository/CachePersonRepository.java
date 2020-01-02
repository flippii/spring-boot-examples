package de.springframework.monitoring.resilience4j.producer.repository;

import de.springframework.monitoring.resilience4j.producer.entity.Person;

import java.util.List;
import java.util.Optional;

public interface CachePersonRepository {

    void save(Person person);

    boolean isEmpty();

    Optional<Person> findById(String id);

    List<Person> findAll();

    void delete(Person user);

}
