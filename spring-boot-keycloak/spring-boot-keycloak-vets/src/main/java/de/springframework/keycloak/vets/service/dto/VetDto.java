package de.springframework.keycloak.vets.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VetDto {

    private String id;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

}
