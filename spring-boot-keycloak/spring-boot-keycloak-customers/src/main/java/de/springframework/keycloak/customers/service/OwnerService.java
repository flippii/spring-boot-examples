package de.springframework.keycloak.customers.service;

import de.springframework.keycloak.customers.document.Owner;
import de.springframework.keycloak.customers.repository.OwnerRepository;
import de.springframework.keycloak.customers.service.dto.OwnerDto;
import de.springframework.keycloak.customers.service.mapper.OwnerMapper;
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
public class OwnerService {

    private final OwnerRepository ownerRepository;
    private final OwnerMapper ownerMapper;

    public Page<OwnerDto> getOwners(Pageable pageable) {
        log.debug("Request to get all Owners.");
        return ownerRepository.findAll(pageable)
                .map(ownerMapper::toDto);
    }

    public Optional<OwnerDto> getOwner(long ownerId) {
        log.debug("Request to get Owner: {}.", ownerId);
        return ownerRepository.findById(ownerId)
                .map(ownerMapper::toDto);
    }

    @Transactional
    public OwnerDto saveOwner(OwnerDto ownerDTO) {
        log.debug("Request to save Owner: {}.", ownerDTO);
        Owner owner = ownerMapper.toEntity(ownerDTO);
        Owner savedOwner = ownerRepository.save(owner);
        return ownerMapper.toDto(savedOwner);
    }

    @Transactional
    public void deleteOwner(long ownerId) {
        log.debug("Request to delete Owner: {}.", ownerId);
        ownerRepository.deleteById(ownerId);
    }

}
