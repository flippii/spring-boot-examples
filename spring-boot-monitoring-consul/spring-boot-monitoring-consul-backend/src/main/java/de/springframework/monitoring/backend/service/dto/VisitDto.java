package de.springframework.monitoring.backend.service.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VisitDto {

    private Long id;
    private LocalDate date;
    private String description;
    private PetDto pet;

}
