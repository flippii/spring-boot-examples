package de.springframework.keycloak.customers.document;

import de.springframework.keycloak.core.document.BaseDocument;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "pets")
public class Pet extends BaseDocument {

    @NotEmpty
    @Field("name")
    private String name;

    @NotEmpty
    @Field("birth_date")
    private LocalDate birthDate;

    @DBRef
    private PetType type;

    @DBRef
    private Owner owner;

}
