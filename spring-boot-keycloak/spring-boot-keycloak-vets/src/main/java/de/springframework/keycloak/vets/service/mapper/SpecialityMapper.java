package de.springframework.keycloak.vets.service.mapper;

import de.springframework.keycloak.core.service.mapper.EntityMapper;
import de.springframework.keycloak.vets.document.Speciality;
import de.springframework.keycloak.vets.service.dto.SpecialityDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SpecialityMapper extends EntityMapper<SpecialityDto, Speciality> {
}
