package de.springframework.monitoring.backend.service.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpecialityDto {

    private Long id;
    private String name;

}
