package de.springframework.keycloak.visits.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VisitDto {

    private String id;

    @NotNull
    private LocalDate date;

    @NotEmpty
    private String description;

    @NotEmpty
    private String petId;

}
