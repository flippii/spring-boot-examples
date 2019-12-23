package de.springframework.monitoring.hystrix.producer.repository;

import de.springframework.monitoring.hystrix.producer.entity.Person;

import java.util.List;
import java.util.Optional;

public interface CachePersonRepository {

    void save(Person person);

    boolean isEmpty();

    Optional<Person> findById(String id);

    List<Person> findAll();

    void delete(Person user);

}
