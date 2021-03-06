package de.springframework.keycloak.customers.web.rest;

import de.springframework.keycloak.core.exception.BadRequestException;
import de.springframework.keycloak.customers.service.OwnerService;
import de.springframework.keycloak.customers.service.dto.OwnerDto;
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
@RequestMapping("api/v1/owners")
@RequiredArgsConstructor
@Timed("petclinic.owners")
public class OwnerRestController {

    private final OwnerService ownerService;

    @GetMapping
    public Page<OwnerDto> getOwner(Pageable pageable) {
        log.debug("REST request to get all Owners.");
        return ownerService.getOwners(pageable);
    }

    @GetMapping("/{ownerId}")
    public OwnerDto getOwner(@PathVariable("ownerId") @Valid @NotNull String ownerId) {
        log.debug("REST request to get Owner: {}.", ownerId);
        return ownerService.getOwner(ownerId)
                .orElseThrow(() -> new BadRequestException("entity.owner.idnotexists", Collections.singletonList(ownerId)));
    }

    @PostMapping
    public ResponseEntity<OwnerDto> createOwner(@RequestBody @Valid OwnerDto ownerDto, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to save Owner: {}.", ownerDto);

        if (StringUtils.isEmpty(ownerDto.getId())) {
            throw new BadRequestException("entity.owner.idexists", Collections.singletonList(ownerDto.getId()));
        }

        OwnerDto result = ownerService.saveOwner(ownerDto);

        return ResponseEntity.created(uriBuilder.path("/api/v1/owners/{id}").buildAndExpand(ownerDto.getId()).toUri())
                .body(result);
    }

    @PutMapping
    public ResponseEntity<OwnerDto> updateOwner(@RequestBody @Valid OwnerDto ownerDto, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to update Owner: {}.", ownerDto);

        if (StringUtils.isEmpty(ownerDto.getId())) {
            return createOwner(ownerDto, uriBuilder);
        }

        OwnerDto result = ownerService.saveOwner(ownerDto);

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{ownerId}")
    public ResponseEntity<Void> deleteOwner(@PathVariable("ownerId") @Valid @NotNull String ownerId) {
        log.debug("REST request to delete Owner: {}.", ownerId);

        ownerService.deleteOwner(ownerId);

        return ResponseEntity.ok().build();
    }

}
