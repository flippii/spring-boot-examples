package de.springframework.monitoring.backend.service.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VetDto {

    private Long id;
    private String firstName;
    private String lastName;

}
