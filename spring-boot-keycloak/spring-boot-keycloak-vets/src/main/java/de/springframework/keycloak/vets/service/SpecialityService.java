package de.springframework.keycloak.vets.service;

import de.springframework.keycloak.vets.document.Speciality;
import de.springframework.keycloak.vets.repository.SpecialityRepository;
import de.springframework.keycloak.vets.service.dto.SpecialityDto;
import de.springframework.keycloak.vets.service.mapper.SpecialityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SpecialityService {

    private final SpecialityRepository specialityRepository;
    private final SpecialityMapper specialityMapper;

    public Page<SpecialityDto> getSpecialties(Pageable pageable) {
        log.debug("Request to get all Specialties.");
        return specialityRepository.findAll(pageable)
                .map(specialityMapper::toDto);
    }

    public Optional<SpecialityDto> getSpeciality(String specialityId) {
        log.debug("Request to get Speciality: {}.", specialityId);
        return specialityRepository.findById(specialityId)
                .map(specialityMapper::toDto);
    }

    @Transactional
    public SpecialityDto saveSpeciality(SpecialityDto specialityDTO) {
        log.debug("Request to save Speciality: {}.", specialityDTO);
        Speciality speciality = specialityMapper.toEntity(specialityDTO);
        Speciality savedSpeciality = specialityRepository.save(speciality);
        return specialityMapper.toDto(savedSpeciality);
    }

    @Transactional
    public void deleteSpeciality(String specialityId) {
        log.debug("Request to delete Speciality: {}.", specialityId);
        specialityRepository.deleteById(specialityId);
    }

}
