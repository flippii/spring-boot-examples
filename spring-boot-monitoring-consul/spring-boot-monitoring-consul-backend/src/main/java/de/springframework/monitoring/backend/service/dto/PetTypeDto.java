package de.springframework.monitoring.backend.service.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PetTypeDto {

    private Long id;
    private String name;

}
