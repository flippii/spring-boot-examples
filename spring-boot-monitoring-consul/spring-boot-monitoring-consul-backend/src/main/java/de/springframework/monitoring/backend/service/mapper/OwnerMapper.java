package de.springframework.monitoring.backend.service.mapper;

import de.springframework.monitoring.backend.entity.Owner;
import de.springframework.monitoring.backend.service.dto.OwnerDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OwnerMapper extends EntityMapper<OwnerDto, Owner> {
}
