package de.springframework.monitoring.resilience4j.producer.repository;

import de.springframework.monitoring.resilience4j.producer.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonJpaRepository extends JpaRepository<Person, Long> {
}
