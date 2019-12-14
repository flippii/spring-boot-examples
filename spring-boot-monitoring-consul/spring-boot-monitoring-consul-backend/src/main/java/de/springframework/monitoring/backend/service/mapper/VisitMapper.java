package de.springframework.monitoring.backend.service.mapper;

import de.springframework.monitoring.backend.entity.Visit;
import de.springframework.monitoring.backend.service.dto.VisitDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {
        PetMapper.class
})
public interface VisitMapper extends EntityMapper<VisitDto, Visit> {
}
