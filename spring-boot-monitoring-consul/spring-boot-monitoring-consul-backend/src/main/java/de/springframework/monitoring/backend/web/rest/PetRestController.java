package de.springframework.monitoring.backend.web.rest;

import de.springframework.monitoring.backend.service.PetService;
import de.springframework.monitoring.backend.service.dto.PetDto;
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
    public PetDto getPet(@PathVariable("petId") long petId) {
        log.debug("REST request to get Pet: {}.", petId);
        return petService.getPet(petId)
                .orElseThrow(() -> new BadRequestException("idexists"));
    }

    @PostMapping
    public ResponseEntity<PetDto> createPet(@RequestBody @Valid PetDto petDto, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to save Pet: {}.", petDto);

        if (petDto.getId() != null) {
            throw new BadRequestException("idexists");
        }

        PetDto result = petService.savePet(petDto);

        return ResponseEntity.created(uriBuilder.path("/api/v1/pets/{id}").buildAndExpand(petDto.getId()).toUri())
                .body(result);
    }

    @PutMapping
    public ResponseEntity<PetDto> updatePet(@RequestBody @Valid PetDto petDto, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to update Pet: {}.", petDto);

        if (petDto.getId() == null) {
            return createPet(petDto, uriBuilder);
        }

        PetDto result = petService.savePet(petDto);

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{petId}")
    public ResponseEntity<Void> deletePet(@PathVariable("petId") long petId) {
        log.debug("REST request to delete Pet: {}.", petId);

        petService.deletePet(petId);

        return ResponseEntity.ok().build();
    }

}
