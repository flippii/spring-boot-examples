package de.springframework.monitoring.backend.service.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SpecialityDto {

    private Long id;
    private String name;

}
