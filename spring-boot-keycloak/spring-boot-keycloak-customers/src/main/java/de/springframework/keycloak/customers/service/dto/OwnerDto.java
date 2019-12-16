package de.springframework.keycloak.customers.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OwnerDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String telephone;

}
