package de.springframework.monitoring.backend.repository;

import de.springframework.monitoring.backend.entity.Visit;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitRepository extends PagingAndSortingRepository<Visit, Long> {
}
