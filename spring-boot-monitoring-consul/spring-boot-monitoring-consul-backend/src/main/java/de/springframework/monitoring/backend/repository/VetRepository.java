package de.springframework.monitoring.backend.repository;

import de.springframework.monitoring.backend.entity.Vet;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VetRepository extends PagingAndSortingRepository<Vet, Long> {
}
