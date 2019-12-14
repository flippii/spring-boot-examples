package de.springframework.monitoring.backend.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@Builder
@Entity
@Table(name = "specialties")
public class Speciality extends BaseEntity<Long> {

    @NotEmpty
    private String name;

    public Speciality(String name) {
        this.name = name;
    }
}
