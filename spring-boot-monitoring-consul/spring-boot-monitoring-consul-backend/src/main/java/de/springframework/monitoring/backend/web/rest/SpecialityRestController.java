package de.springframework.monitoring.backend.web.rest;

import de.springframework.monitoring.backend.service.SpecialityService;
import de.springframework.monitoring.backend.service.dto.SpecialityDto;
import de.springframework.monitoring.backend.web.rest.errors.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/specialities")
@RequiredArgsConstructor
public class SpecialityRestController {

    private final SpecialityService specialityService;

    @GetMapping
    public Page<SpecialityDto> getSpecialities(Pageable pageable) {
        log.debug("REST request to get all Specialities.");
        return specialityService.getSpecialties(pageable);
    }

    @GetMapping("/{specialityId}")
    public SpecialityDto getSpeciality(@PathVariable("specialityId") long specialityId) {
        log.debug("REST request to get Speciality: {}.", specialityId);
        return specialityService.getSpeciality(specialityId)
                .orElseThrow(() -> new BadRequestException("entity.speciality.idnotexists", List.of(specialityId)));
    }

    @PostMapping
    public ResponseEntity<SpecialityDto> createSpeciality(@RequestBody @Valid SpecialityDto specialityDto, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to save Speciality: {}.", specialityDto);

        if (specialityDto.getId() != null) {
            throw new BadRequestException("entity.speciality.idexists", List.of(specialityDto.getId()));
        }

        SpecialityDto result = specialityService.saveSpeciality(specialityDto);

        return ResponseEntity.created(uriBuilder.path("/api/v1/specialities/{id}").buildAndExpand(specialityDto.getId()).toUri())
                .body(result);
    }

    @PutMapping
    public ResponseEntity<SpecialityDto> updateSpeciality(@RequestBody @Valid SpecialityDto specialityDto, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to update Speciality: {}.", specialityDto);

        if (specialityDto.getId() == null) {
            return createSpeciality(specialityDto, uriBuilder);
        }

        SpecialityDto result = specialityService.saveSpeciality(specialityDto);

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{specialityId}")
    public ResponseEntity<Void> deleteSpeciality(@PathVariable("specialityId") long specialityId) {
        log.debug("REST request to delete Speciality: {}.", specialityId);

        specialityService.deleteSpeciality(specialityId);

        return ResponseEntity.ok()
                .build();
    }

}
