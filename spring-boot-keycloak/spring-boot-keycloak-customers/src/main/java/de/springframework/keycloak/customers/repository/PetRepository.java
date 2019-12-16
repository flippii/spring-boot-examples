package de.springframework.keycloak.customers.repository;

import de.springframework.keycloak.customers.document.Pet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends MongoRepository<Pet, Long> {
}
