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
import java.util.Collections;

@Slf4j
@RestController
@RequestMapping("api/v1/pets/{petId}/visits")
@RequiredArgsConstructor
@Timed("petclinic.visit")
public class VisitRestController {

    private final VisitService visitService;

    @GetMapping
    public Page<VisitDto> getVisits(@PathVariable("petId") String petId, Pageable pageable) {
        log.debug("REST request to get all Visits from Pet: {}.", petId);
        return visitService.getVisits(petId, pageable);
    }

    @PostMapping
    public ResponseEntity<VisitDto> createVisit(@RequestBody @Valid VisitDto visitDto, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to save Visit: {}.", visitDto);

        if (StringUtils.isEmpty(visitDto.getId())) {
            throw new BadRequestException("entity.visit.idexists", Collections.singletonList(visitDto.getId()));
        }

        VisitDto result = visitService.saveVisit(visitDto);

        return ResponseEntity.created(uriBuilder.path("/api/v1/pets/{petId}/{id}").buildAndExpand(visitDto.getPetId(), visitDto.getId()).toUri())
                .body(result);
    }

    @PutMapping
    public ResponseEntity<VisitDto> updateVisit(@RequestBody @Valid VisitDto visitDto, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to update Visit: {}.", visitDto);

        if (StringUtils.isEmpty(visitDto.getId())) {
            return createVisit(visitDto, uriBuilder);
        }

        VisitDto result = visitService.saveVisit( visitDto);

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{visitId}")
    public ResponseEntity<Void> deleteVisit(@PathVariable("petId") String petId, @PathVariable("visitId") String visitId) {
        log.debug("REST request to delete Visit: {} from Pet: {}.", visitId, petId);

        visitService.deleteVisit(petId, visitId);

        return ResponseEntity.ok().build();
    }

}
