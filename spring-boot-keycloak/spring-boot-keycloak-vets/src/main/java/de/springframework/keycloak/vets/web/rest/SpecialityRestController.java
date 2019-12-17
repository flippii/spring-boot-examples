package de.springframework.keycloak.vets.web.rest;

import de.springframework.keycloak.core.exception.BadRequestException;
import de.springframework.keycloak.vets.service.SpecialityService;
import de.springframework.keycloak.vets.service.dto.SpecialityDto;
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
import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/specialities")
@RequiredArgsConstructor
@Timed("petclinic.specialities")
public class SpecialityRestController {

    private final SpecialityService specialityService;

    @GetMapping
    public Page<SpecialityDto> getSpecialities(Pageable pageable) {
        log.debug("REST request to get all Specialities.");
        return specialityService.getSpecialties(pageable);
    }

    @GetMapping("/{specialityId}")
    public SpecialityDto getSpeciality(@PathVariable("specialityId") @Valid @NotNull String specialityId) {
        log.debug("REST request to get Speciality: {}.", specialityId);
        return specialityService.getSpeciality(specialityId)
                .orElseThrow(() -> new BadRequestException("entity.speciality.idnotexists", List.of(specialityId)));
    }

    @PostMapping
    public ResponseEntity<SpecialityDto> createSpeciality(@RequestBody @Valid SpecialityDto specialityDto,
                                                          UriComponentsBuilder uriBuilder) {

        log.debug("REST request to save Speciality: {}.", specialityDto);

        if (StringUtils.isEmpty(specialityDto.getId())) {
            throw new BadRequestException("entity.speciality.idexists", List.of(specialityDto.getId()));
        }

        SpecialityDto result = specialityService.saveSpeciality(specialityDto);

        return ResponseEntity.created(uriBuilder.path("/api/v1/specialities/{id}").buildAndExpand(specialityDto.getId()).toUri())
                .body(result);
    }

    @PutMapping
    public ResponseEntity<SpecialityDto> updateSpeciality(@RequestBody @Valid SpecialityDto specialityDto,
                                                          UriComponentsBuilder uriBuilder) {

        log.debug("REST request to update Speciality: {}.", specialityDto);

        if (StringUtils.isEmpty(specialityDto.getId())) {
            return createSpeciality(specialityDto, uriBuilder);
        }

        SpecialityDto result = specialityService.saveSpeciality(specialityDto);

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{specialityId}")
    public ResponseEntity<Void> deleteSpeciality(@PathVariable("specialityId") @Valid @NotNull String specialityId) {
        log.debug("REST request to delete Speciality: {}.", specialityId);

        specialityService.deleteSpeciality(specialityId);

        return ResponseEntity.ok()
                .build();
    }

}
