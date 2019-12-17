package de.springframework.keycloak.visits.service.mapper;

import de.springframework.keycloak.core.service.mapper.EntityMapper;
import de.springframework.keycloak.visits.document.Visit;
import de.springframework.keycloak.visits.service.dto.VisitDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VisitMapper extends EntityMapper<VisitDto, Visit> {
}
