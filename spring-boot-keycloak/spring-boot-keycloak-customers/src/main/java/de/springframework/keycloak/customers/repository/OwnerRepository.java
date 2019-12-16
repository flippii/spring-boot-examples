package de.springframework.keycloak.customers.repository;

import de.springframework.keycloak.customers.document.Owner;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OwnerRepository extends MongoRepository<Owner, Long> {

    Optional<Owner> findByFirstNameAndLastName(String firstName, String lastName);

}
