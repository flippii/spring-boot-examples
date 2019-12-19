package de.springframework.keycloak.customers.web.rest;

import de.springframework.keycloak.core.exception.BadRequestException;
import de.springframework.keycloak.customers.service.PetTypeService;
import de.springframework.keycloak.customers.service.dto.PetTypeDto;
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
import javax.validation.constraints.NotNull;
import java.util.Collections;

@Slf4j
@RestController
@RequestMapping("api/v1/petTypes")
@RequiredArgsConstructor
@Timed("petclinic.petTypes")
public class PetTypeRestController {

    private final PetTypeService petTypeService;

    @GetMapping
    public Page<PetTypeDto> getPetTypes(Pageable pageable) {
        log.debug("REST request to get all PetTypes.");
        return petTypeService.getPetTypes(pageable);
    }

    @GetMapping("/{petTypeId}")
    public PetTypeDto getPetType(@PathVariable("petTypeId") @Valid @NotNull String petTypeId) {
        log.debug("REST request to get PetType: {}.", petTypeId);
        return petTypeService.getPetType(petTypeId)
                .orElseThrow(() -> new BadRequestException("entity.petType.idnotexists", Collections.singletonList(petTypeId)));
    }

    @PostMapping
    public ResponseEntity<PetTypeDto> createPetType(@RequestBody @Valid PetTypeDto petTypeDto, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to save PetType: {}.", petTypeDto);

        if (StringUtils.isEmpty(petTypeDto.getId())) {
            throw new BadRequestException("entity.petType.idexists", Collections.singletonList(petTypeDto.getId()));
        }

        PetTypeDto result = petTypeService.savePetType(petTypeDto);

        return ResponseEntity.created(uriBuilder.path("/api/v1/petTypes/{id}").buildAndExpand(petTypeDto.getId()).toUri())
                .body(result);
    }

    @PutMapping
    public ResponseEntity<PetTypeDto> updatePetType(@RequestBody @Valid PetTypeDto petTypeDto, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to update PetType: {}.", petTypeDto);

        if (StringUtils.isEmpty(petTypeDto.getId())) {
            return createPetType(petTypeDto, uriBuilder);
        }

        PetTypeDto result = petTypeService.savePetType(petTypeDto);

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{petTypeId}")
    public ResponseEntity<Void> deletePet(@PathVariable("petTypeId") @Valid @NotNull String petTypeId) {
        log.debug("REST request to delete PetType: {}.", petTypeId);

        petTypeService.deletePetType(petTypeId);

        return ResponseEntity.ok()
                .build();
    }

}
