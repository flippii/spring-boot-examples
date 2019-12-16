package de.springframework.keycloak.customers.repository;

import de.springframework.keycloak.customers.document.PetType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PetTypeRepository extends MongoRepository<PetType, Long> {

    Optional<PetType> findByName(String name);

}
