package de.springframework.monitoring.backend.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "vets")
public class Vet extends BaseEntity<Long> {

    @NotEmpty
    protected String firstName;

    @NotEmpty
    protected String lastName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "vet_specialties", joinColumns = @JoinColumn(name = "vet_id"), inverseJoinColumns = @JoinColumn(name = "specialty_id"))
    private Set<Speciality> specialties;

    public Vet(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void addSpecialty(Speciality speciality) {
        if (specialties == null) {
            specialties = new HashSet<>();
        }

        specialties.add(speciality);
    }

}
