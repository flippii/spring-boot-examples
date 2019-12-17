package de.springframework.keycloak.visits.repository;

import de.springframework.keycloak.visits.document.Visit;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VisitRepository extends MongoRepository<Visit, String> {
}
