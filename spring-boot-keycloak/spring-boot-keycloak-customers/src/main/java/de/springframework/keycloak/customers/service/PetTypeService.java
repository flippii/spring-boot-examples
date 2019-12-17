package de.springframework.keycloak.customers.service;

import de.springframework.keycloak.customers.document.PetType;
import de.springframework.keycloak.customers.repository.PetTypeRepository;
import de.springframework.keycloak.customers.service.dto.PetTypeDto;
import de.springframework.keycloak.customers.service.mapper.PetTypeMapper;
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
public class PetTypeService {

    private final PetTypeRepository petTypeRepository;
    private final PetTypeMapper petTypeMapper;

    public Page<PetTypeDto> getPetTypes(Pageable pageable) {
        log.debug("Request to get all PetTypes.");
        return petTypeRepository.findAll(pageable)
                .map(petTypeMapper::toDto);
    }

    public Optional<PetTypeDto> getPetType(String petTypeId) {
        log.debug("Request to get PetType: {}.", petTypeId);
        return petTypeRepository.findById(petTypeId)
                .map(petTypeMapper::toDto);
    }

    @Transactional
    public PetTypeDto savePetType(PetTypeDto petTypeDTO) {
        log.debug("Request to save PetType: {}.", petTypeDTO);
        PetType petType = petTypeMapper.toEntity(petTypeDTO);
        PetType savedPetType = petTypeRepository.save(petType);
        return petTypeMapper.toDto(savedPetType);
    }

    @Transactional
    public void deletePetType(String petTypeId) {
        log.debug("Request to delete PetType: {}.", petTypeId);
        petTypeRepository.deleteById(petTypeId);
    }

}
