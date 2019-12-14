package de.springframework.monitoring.backend.service.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class PetDto {

    private Long id;
    private String name;
    private LocalDate birthDate;
    private PetTypeDto type;
    private OwnerDto owner;

}
