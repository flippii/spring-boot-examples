package de.springframework.keycloak.vets.web.rest;

import de.springframework.keycloak.core.exception.BadRequestException;
import de.springframework.keycloak.vets.service.VetService;
import de.springframework.keycloak.vets.service.dto.VetDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

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
    public VetDto getVet(@PathVariable("vetId") @Valid @NotNull String vetId) {
        log.debug("REST request to get Vet: {}.", vetId);
        return vetService.getVet(vetId)
                .orElseThrow(() -> new BadRequestException("entity.vet.idnotexists", List.of(vetId)));
    }

    @PostMapping
    public ResponseEntity<VetDto> createVet(@RequestBody @Valid VetDto vetDto, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to save Vet: {}.", vetDto);

        if (StringUtils.isEmpty(vetDto.getId())) {
            throw new BadRequestException("entity.vet.idexists", List.of(vetDto.getId()));
        }

        VetDto result = vetService.saveVet(vetDto);

        return ResponseEntity.created(uriBuilder.path("/api/v1/vets/{id}").buildAndExpand(vetDto.getId()).toUri())
                .body(result);
    }

    @PutMapping
    public ResponseEntity<VetDto> updateVet(@RequestBody @Valid VetDto vetDto, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to update Vet: {}.", vetDto);

        if (StringUtils.isEmpty(vetDto.getId())) {
            return createVet(vetDto, uriBuilder);
        }

        VetDto result = vetService.saveVet(vetDto);

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{vetId}")
    public ResponseEntity<Void> deletePet(@PathVariable("vetId") @Valid @NotNull String vetId) {
        log.debug("REST request to delete Vet: {}.", vetId);

        vetService.deleteVet(vetId);

        return ResponseEntity.ok()
                .build();
    }

}
