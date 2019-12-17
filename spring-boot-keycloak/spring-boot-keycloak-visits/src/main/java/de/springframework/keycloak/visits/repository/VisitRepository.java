package de.springframework.keycloak.visits.repository;

import de.springframework.keycloak.visits.document.Visit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VisitRepository extends MongoRepository<Visit, String> {

    Page<Visit> findAllByPetId(String petId, Pageable pageable);

    void deleteByIdAndPetId(String id, String petId);

}
