package de.springframework.keycloak.customers.service.mapper;

import de.springframework.keycloak.customers.document.Owner;
import de.springframework.keycloak.customers.service.dto.OwnerDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OwnerMapper extends EntityMapper<OwnerDto, Owner> {
}
