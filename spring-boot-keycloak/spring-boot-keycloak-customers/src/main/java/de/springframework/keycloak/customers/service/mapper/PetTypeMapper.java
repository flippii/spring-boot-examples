package de.springframework.keycloak.customers.service.mapper;

import de.springframework.keycloak.core.service.mapper.EntityMapper;
import de.springframework.keycloak.customers.document.PetType;
import de.springframework.keycloak.customers.service.dto.PetTypeDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PetTypeMapper extends EntityMapper<PetTypeDto, PetType> {
}
