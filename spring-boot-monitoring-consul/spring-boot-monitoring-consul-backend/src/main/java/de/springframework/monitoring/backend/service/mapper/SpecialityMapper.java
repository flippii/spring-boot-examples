package de.springframework.monitoring.backend.service.mapper;

import de.springframework.monitoring.backend.entity.Speciality;
import de.springframework.monitoring.backend.service.dto.SpecialityDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SpecialityMapper extends EntityMapper<SpecialityDto, Speciality> {
}
