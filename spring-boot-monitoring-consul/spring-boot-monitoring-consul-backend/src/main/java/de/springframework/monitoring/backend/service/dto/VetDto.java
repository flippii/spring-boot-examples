package de.springframework.monitoring.backend.service.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class VetDto {

    private Long id;
    private String firstName;
    private String lastName;

}
