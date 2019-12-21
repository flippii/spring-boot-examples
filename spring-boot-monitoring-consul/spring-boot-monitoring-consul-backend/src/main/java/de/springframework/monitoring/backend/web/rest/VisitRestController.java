package de.springframework.monitoring.backend.web.rest;

import de.springframework.monitoring.backend.service.VisitService;
import de.springframework.monitoring.backend.service.dto.VisitDto;
import de.springframework.monitoring.backend.web.rest.errors.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.Collections;

@Slf4j
@RestController
@RequestMapping("api/v1/visits")
@RequiredArgsConstructor
public class VisitRestController {

    private final VisitService visitService;

    @GetMapping
    public Page<VisitDto> getVets(Pageable pageable) {
        log.debug("REST request to get all Visits.");
        return visitService.getVisits(pageable);
    }

    @GetMapping("/{visitId}")
    public VisitDto getVet(@PathVariable("visitId") long visitId) {
        log.debug("REST request to get Visit: {}.", visitId);
        return visitService.getVisit(visitId)
                .orElseThrow(() -> new BadRequestException("entity.visit.idnotexists", Collections.singletonList(visitId)));
    }

    @PostMapping
    public ResponseEntity<VisitDto> createVisit(@RequestBody @Valid VisitDto visitDto, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to save Visit: {}.", visitDto);

        if (visitDto.getId() != null) {
            throw new BadRequestException("entity.visit.idexists", Collections.singletonList(visitDto.getId()));
        }

        VisitDto result = visitService.saveVisit(visitDto);

        return ResponseEntity.created(uriBuilder.path("/api/v1/visits/{id}").buildAndExpand(visitDto.getId()).toUri())
                .body(result);
    }

    @PutMapping
    public ResponseEntity<VisitDto> updateVet(@RequestBody @Valid VisitDto visitDto, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to update Visit: {}.", visitDto);

        if (visitDto.getId() == null) {
            return createVisit(visitDto, uriBuilder);
        }

        VisitDto result = visitService.saveVisit(visitDto);

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{visitId}")
    public ResponseEntity<Void> deletePet(@PathVariable("visitId") long visitId) {
        log.debug("REST request to delete Visit: {}.", visitId);

        visitService.deleteVisit(visitId);

        return ResponseEntity.ok()
                .build();
    }

}
