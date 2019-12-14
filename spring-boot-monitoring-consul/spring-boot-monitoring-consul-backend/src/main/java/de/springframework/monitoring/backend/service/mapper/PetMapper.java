package de.springframework.monitoring.backend.service.mapper;

import de.springframework.monitoring.backend.entity.Pet;
import de.springframework.monitoring.backend.service.dto.PetDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {
        PetTypeMapper.class,
        OwnerMapper.class
})
public interface PetMapper extends EntityMapper<PetDto, Pet> {
}
