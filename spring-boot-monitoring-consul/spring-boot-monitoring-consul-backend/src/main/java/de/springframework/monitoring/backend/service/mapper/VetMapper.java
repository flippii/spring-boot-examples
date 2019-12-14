package de.springframework.monitoring.backend.service.mapper;

import de.springframework.monitoring.backend.entity.Vet;
import de.springframework.monitoring.backend.service.dto.VetDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VetMapper extends EntityMapper<VetDto, Vet> {
}
