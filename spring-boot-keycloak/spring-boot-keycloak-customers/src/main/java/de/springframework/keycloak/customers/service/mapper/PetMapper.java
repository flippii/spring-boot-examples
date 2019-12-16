package de.springframework.keycloak.customers.service.mapper;

import de.springframework.keycloak.customers.document.Pet;
import de.springframework.keycloak.customers.service.dto.PetDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {
        PetTypeMapper.class,
        OwnerMapper.class
})
public interface PetMapper extends EntityMapper<PetDto, Pet> {
}
