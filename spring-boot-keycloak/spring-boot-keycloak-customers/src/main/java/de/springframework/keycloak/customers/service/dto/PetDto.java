package de.springframework.keycloak.customers.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PetDto {

    private String id;

    @NotEmpty
    private String name;

    @NotEmpty
    private LocalDate birthDate;

    private PetTypeDto type;
    private OwnerDto owner;

}
