package de.springframework.keycloak.vets.repository;

import de.springframework.keycloak.vets.document.Vet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VetRepository extends MongoRepository<Vet, String> {
}
