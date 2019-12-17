package de.springframework.keycloak.vets.document;

import de.springframework.keycloak.core.document.BaseDocument;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@Builder
@Document(collection = "specialities")
public class Speciality extends BaseDocument {

    @NotEmpty
    @Field("name")
    private String name;

    public Speciality(String name) {
        this.name = name;
    }

}
