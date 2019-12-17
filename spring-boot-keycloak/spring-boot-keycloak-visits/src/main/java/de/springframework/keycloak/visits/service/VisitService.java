package de.springframework.keycloak.visits.service;

import de.springframework.keycloak.visits.document.Visit;
import de.springframework.keycloak.visits.repository.VisitRepository;
import de.springframework.keycloak.visits.service.dto.VisitDto;
import de.springframework.keycloak.visits.service.mapper.VisitMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VisitService {

    private final VisitRepository visitRepository;
    private final VisitMapper visitMapper;

    public Page<VisitDto> getVisits(String petId, Pageable pageable) {
        log.debug("Request to get all Visits for Pet: {}.", petId);
        return visitRepository.findAllByPetId(petId, pageable)
                .map(visitMapper::toDto);
    }

    @Transactional
    public VisitDto saveVisit(VisitDto visitDTO) {
        log.debug("Request to save Visit: {}.", visitDTO);
        Visit visit = visitMapper.toEntity(visitDTO);
        Visit savedVisit = visitRepository.save(visit);
        return visitMapper.toDto(savedVisit);
    }

    @Transactional
    public void deleteVisit(String petId, String visitId) {
        log.debug("Request to delete Visit: {} for Pet: {}.", visitId, petId);
        visitRepository.deleteByIdAndPetId(visitId, petId);
    }

}
