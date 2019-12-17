package de.springframework.keycloak.vets.service;

import de.springframework.keycloak.vets.document.Vet;
import de.springframework.keycloak.vets.repository.VetRepository;
import de.springframework.keycloak.vets.service.dto.VetDto;
import de.springframework.keycloak.vets.service.mapper.VetMapper;
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
public class VetService {

    private final VetRepository vetRepository;
    private final VetMapper vetMapper;

    public Page<VetDto> getVets(Pageable pageable) {
        log.debug("Request to get all Vets.");
        return vetRepository.findAll(pageable)
                .map(vetMapper::toDto);
    }

    public Optional<VetDto> getVet(String vetId) {
        log.debug("Request to get Vet: {}.", vetId);
        return vetRepository.findById(vetId)
                .map(vetMapper::toDto);
    }

    @Transactional
    public VetDto saveVet(VetDto vetDTO) {
        log.debug("Request to save Vet: {}.", vetDTO);
        Vet vet = vetMapper.toEntity(vetDTO);
        Vet savedVet = vetRepository.save(vet);
        return vetMapper.toDto(savedVet);
    }

    @Transactional
    public void deleteVet(String vetId) {
        log.debug("Request to delete Vet: {}.", vetId);
        vetRepository.deleteById(vetId);
    }

}
