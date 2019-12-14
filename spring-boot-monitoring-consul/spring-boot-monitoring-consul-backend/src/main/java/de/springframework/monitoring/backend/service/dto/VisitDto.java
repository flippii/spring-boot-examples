package de.springframework.monitoring.backend.service.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class VisitDto {

    private Long id;
    private LocalDate date;
    private String description;
    private PetDto pet;

}
