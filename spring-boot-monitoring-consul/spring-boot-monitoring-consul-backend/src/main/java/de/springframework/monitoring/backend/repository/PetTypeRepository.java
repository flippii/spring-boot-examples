package de.springframework.monitoring.backend.repository;

import de.springframework.monitoring.backend.entity.PetType;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PetTypeRepository extends PagingAndSortingRepository<PetType, Long> {

    Optional<PetType> findByName(String name);

}
