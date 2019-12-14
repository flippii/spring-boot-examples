package de.springframework.monitoring.backend.service.dto;

import lombok.*;

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
