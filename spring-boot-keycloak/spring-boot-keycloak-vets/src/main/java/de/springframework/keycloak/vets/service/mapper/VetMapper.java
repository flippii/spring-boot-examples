package de.springframework.keycloak.vets.service.mapper;

import de.springframework.keycloak.core.service.mapper.EntityMapper;
import de.springframework.keycloak.vets.document.Vet;
import de.springframework.keycloak.vets.service.dto.VetDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VetMapper extends EntityMapper<VetDto, Vet> {
}
