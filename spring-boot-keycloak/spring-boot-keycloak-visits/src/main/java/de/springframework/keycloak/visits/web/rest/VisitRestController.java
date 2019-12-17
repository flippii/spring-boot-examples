package de.springframework.keycloak.visits.web.rest;

import de.springframework.keycloak.core.exception.BadRequestException;
import de.springframework.keycloak.visits.service.VisitService;
import de.springframework.keycloak.visits.service.dto.VisitDto;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/pets/{petId}/visits")
@RequiredArgsConstructor
@Timed("petclinic.visit")
public class VisitRestController {

    private final VisitService visitService;

    @GetMapping
    public Page<VisitDto> getVisits(@PathVariable("petId") String petId, Pageable pageable) {
        log.debug("REST request to get all Visits from pet: {}.", petId);
        return visitService.getVisits(petId, pageable);
    }

    @GetMapping("/{visitId}")
    public VisitDto getVisit(@PathVariable("petId") String petId, @PathVariable("visitId") String visitId) {
        log.debug("REST request to get Visit: {}.", visitId);
        return visitService.getVisit(petId, visitId)
                .orElseThrow(() -> new BadRequestException("entity.visit.idnotexists", List.of(visitId)));
    }

    @PostMapping
    public ResponseEntity<VisitDto> createVisit(@PathVariable("petId") String petId, @RequestBody @Valid VisitDto visitDto,
                                                UriComponentsBuilder uriBuilder) {

        log.debug("REST request to save Visit: {}.", visitDto);

        if (StringUtils.isEmpty(visitDto.getId())) {
            throw new BadRequestException("entity.visit.idexists", List.of(visitDto.getId()));
        }

        VisitDto result = visitService.saveVisit(visitDto);

        return ResponseEntity.created(uriBuilder.path("/api/v1/visits/{id}").buildAndExpand(visitDto.getId()).toUri())
                .body(result);
    }

    @PutMapping
    public ResponseEntity<VisitDto> updateVet(@PathVariable("petId") String petId, @RequestBody @Valid VisitDto visitDto,
                                              UriComponentsBuilder uriBuilder) {

        log.debug("REST request to update Visit: {}.", visitDto);

        if (StringUtils.isEmpty(visitDto.getId())) {
            return createVisit(petId, visitDto, uriBuilder);
        }

        VisitDto result = visitService.saveVisit(visitDto);

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{visitId}")
    public ResponseEntity<Void> deletePet(@PathVariable("petId") String petId, @PathVariable("visitId") String visitId) {
        log.debug("REST request to delete Visit: {}.", visitId);

        visitService.deleteVisit(visitId);

        return ResponseEntity.ok()
                .build();
    }

}
