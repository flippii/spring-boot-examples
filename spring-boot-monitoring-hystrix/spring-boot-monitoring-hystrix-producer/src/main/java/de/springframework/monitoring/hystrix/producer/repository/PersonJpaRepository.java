package de.springframework.monitoring.hystrix.producer.repository;

import de.springframework.monitoring.hystrix.producer.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonJpaRepository extends JpaRepository<Person, Long> {
}
