package de.springframework.monitoring.backend.service.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PetTypeDto {

    private Long id;
    private String name;

}
