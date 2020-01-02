package de.springframework.monitoring.resilience4j.producer.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonDto {

    private Long id;
    private String firstName;
    private String lastName;

}
