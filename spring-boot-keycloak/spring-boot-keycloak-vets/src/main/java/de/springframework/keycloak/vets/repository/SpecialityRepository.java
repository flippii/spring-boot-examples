package de.springframework.keycloak.vets.repository;

import de.springframework.keycloak.vets.document.Speciality;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpecialityRepository extends MongoRepository<Speciality, String> {

    Optional<Speciality> findByName(String name);

}
