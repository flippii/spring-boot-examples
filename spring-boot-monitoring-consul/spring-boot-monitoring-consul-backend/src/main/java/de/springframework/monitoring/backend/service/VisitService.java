package de.springframework.monitoring.backend.service;

import de.springframework.monitoring.backend.entity.Visit;
import de.springframework.monitoring.backend.repository.VisitRepository;
import de.springframework.monitoring.backend.service.dto.VisitDto;
import de.springframework.monitoring.backend.service.mapper.VisitMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VisitService {

    private final VisitRepository visitRepository;
    private final VisitMapper visitMapper;

    public Page<VisitDto> getVisits(Pageable pageable) {
        log.debug("Request to get all Visits.");
        return visitRepository.findAll(pageable)
                .map(visitMapper::toDto);
    }

    public Optional<VisitDto> getVisit(long visitId) {
        log.debug("Request to get Visit: {}.", visitId);
        return visitRepository.findById(visitId)
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
    public void deleteVisit(long visitId) {
        log.debug("Request to delete Visit: {}.", visitId);
        visitRepository.deleteById(visitId);
    }

}
