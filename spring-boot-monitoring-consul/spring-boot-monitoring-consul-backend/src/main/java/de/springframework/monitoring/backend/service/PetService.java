package de.springframework.monitoring.backend.service;

import de.springframework.monitoring.backend.entity.Pet;
import de.springframework.monitoring.backend.repository.PetRepository;
import de.springframework.monitoring.backend.service.dto.PetDto;
import de.springframework.monitoring.backend.service.mapper.PetMapper;
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
public class PetService {

    private final PetRepository petRepository;
    private final PetMapper petMapper;

    public Page<PetDto> getPets(Pageable pageable) {
        log.debug("Request to get all Pets.");
        return petRepository.findAll(pageable)
                .map(petMapper::toDto);
    }

    public Optional<PetDto> getPet(long petId) {
        log.debug("Request to get Pet: {}.", petId);
        return petRepository.findById(petId)
                .map(petMapper::toDto);
    }

    @Transactional
    public PetDto savePet(PetDto petDTO) {
        log.debug("Request to save Pet: {}.", petDTO);
        Pet pet = petMapper.toEntity(petDTO);
        Pet savedPet = petRepository.save(pet);
        return petMapper.toDto(savedPet);
    }

    @Transactional
    public void deletePet(long petId) {
        log.debug("Request to delete Pet: {}.", petId);
        petRepository.deleteById(petId);
    }

}
