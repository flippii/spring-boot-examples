package de.springframework.monitoring.backend.repository;

import de.springframework.monitoring.backend.entity.Owner;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OwnerRepository extends PagingAndSortingRepository<Owner, Long> {

    Optional<Owner> findByFirstNameAndLastName(String firstName, String lastName);

}
