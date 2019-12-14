package de.springframework.monitoring.backend.web.rest;

import de.springframework.monitoring.backend.service.VetService;
import de.springframework.monitoring.backend.service.dto.VetDto;
import de.springframework.monitoring.backend.web.rest.errors.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("api/v1/vets")
@RequiredArgsConstructor
public class VetRestController {

    private final VetService vetService;

    @GetMapping
    public Page<VetDto> getVets(Pageable pageable) {
        log.debug("REST request to get all Vets.");
        return vetService.getVets(pageable);
    }

    @GetMapping("/{vetId}")
    public VetDto getVet(@PathVariable("vetId") long vetId) {
        log.debug("REST request to get Vet: {}.", vetId);
        return vetService.getVet(vetId)
                .orElseThrow(() -> new BadRequestException("idexists"));
    }

    @PostMapping
    public ResponseEntity<VetDto> createVet(@RequestBody @Valid VetDto vetDTO, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to save Vet: {}.", vetDTO);

        if (vetDTO.getId() != null) {
            throw new BadRequestException("idexists");
        }

        VetDto result = vetService.saveVet(vetDTO);

        return ResponseEntity.created(uriBuilder.path("/api/v1/vets/{id}").buildAndExpand(vetDTO.getId()).toUri())
                .body(result);
    }

    @PutMapping
    public ResponseEntity<VetDto> updateVet(@RequestBody @Valid VetDto vetDTO, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to update Vet: {}.", vetDTO);

        if (vetDTO.getId() == null) {
            return createVet(vetDTO, uriBuilder);
        }

        VetDto result = vetService.saveVet(vetDTO);

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{vetId}")
    public ResponseEntity<Void> deletePet(@PathVariable("vetId") long vetId) {
        log.debug("REST request to delete Vet: {}.", vetId);

        vetService.deleteVet(vetId);

        return ResponseEntity.ok()
                .build();
    }

}
