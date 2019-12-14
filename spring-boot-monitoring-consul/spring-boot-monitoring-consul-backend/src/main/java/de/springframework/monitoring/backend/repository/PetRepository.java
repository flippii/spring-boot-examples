package de.springframework.monitoring.backend.repository;

import de.springframework.monitoring.backend.entity.Pet;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends PagingAndSortingRepository<Pet, Long> {
}
