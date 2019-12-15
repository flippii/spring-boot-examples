package de.springframework.monitoring.backend.service.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PetDto {

    private Long id;
    private String name;
    private LocalDate birthDate;
    private PetTypeDto type;
    private OwnerDto owner;

}
