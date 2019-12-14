package de.springframework.monitoring.backend.repository;

import de.springframework.monitoring.backend.entity.Speciality;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpecialityRepository extends PagingAndSortingRepository<Speciality, Long> {

    Optional<Speciality> findByName(String name);

}
