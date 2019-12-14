package de.springframework.monitoring.backend.service.mapper;

import de.springframework.monitoring.backend.entity.PetType;
import de.springframework.monitoring.backend.service.dto.PetTypeDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PetTypeMapper extends EntityMapper<PetTypeDto, PetType> {
}
