package de.springframework.monitoring.backend.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "pets")
public class Pet extends BaseEntity<Long>  {

    @NotEmpty
    private String name;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate birthDate;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private PetType type;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pet", fetch = FetchType.EAGER)
    private Set<Visit> visits;

    public Pet(String name, LocalDate birthDate, PetType type, Owner owner) {
        this.name = name;
        this.birthDate = birthDate;
        this.type = type;
        this.owner = owner;
    }

    public Pet addVisit(Visit visit) {
        if (visits == null) {
            visits = new HashSet<>();
        }

        visits.add(visit);
        visit.setPet(this);
        return this;
    }

}
