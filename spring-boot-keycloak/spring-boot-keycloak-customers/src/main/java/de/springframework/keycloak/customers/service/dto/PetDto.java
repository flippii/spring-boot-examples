package de.springframework.keycloak.customers.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
