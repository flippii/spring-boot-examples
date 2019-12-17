package de.springframework.keycloak.customers.web.rest;

import de.springframework.keycloak.customers.service.PetService;
import de.springframework.keycloak.customers.service.dto.PetDto;
import de.springframework.keycloak.customers.web.rest.errors.BadRequestException;
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
@RequestMapping("api/v1/pets")
@RequiredArgsConstructor
public class PetRestController {

    private final PetService petService;

    @GetMapping
    public Page<PetDto> getPets(Pageable pageable) {
        log.debug("REST request to get all Pets.");
        return petService.getPets(pageable);
    }

    @GetMapping("/{petId}")
    public PetDto getPet(@PathVariable("petId") @Valid @NotNull String petId) {
        log.debug("REST request to get Pet: {}.", petId);
        return petService.getPet(petId)
                .orElseThrow(() -> new BadRequestException("entity.pet.idnotexists", List.of(petId)));
    }

    @PostMapping
    public ResponseEntity<PetDto> createPet(@RequestBody @Valid PetDto petDto, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to save Pet: {}.", petDto);

        if (StringUtils.isEmpty(petDto.getId())) {
            throw new BadRequestException("entity.pet.idexists", List.of(petDto.getId()));
        }

        PetDto result = petService.savePet(petDto);

        return ResponseEntity.created(uriBuilder.path("/api/v1/pets/{id}").buildAndExpand(petDto.getId()).toUri())
                .body(result);
    }

    @PutMapping
    public ResponseEntity<PetDto> updatePet(@RequestBody @Valid PetDto petDto, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to update Pet: {}.", petDto);

        if (StringUtils.isEmpty(petDto.getId())) {
            return createPet(petDto, uriBuilder);
        }

        PetDto result = petService.savePet(petDto);

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{petId}")
    public ResponseEntity<Void> deletePet(@PathVariable("petId") @Valid @NotNull String petId) {
        log.debug("REST request to delete Pet: {}.", petId);

        petService.deletePet(petId);

        return ResponseEntity.ok().build();
    }

}
