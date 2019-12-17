package de.springframework.keycloak.vets.document;

import de.springframework.keycloak.core.document.BaseDocument;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "vets")
public class Vet extends BaseDocument {

    @NotEmpty
    @Field("first_name")
    private String firstName;

    @NotEmpty
    @Field("last_name")
    private String lastName;

    @DBRef
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
