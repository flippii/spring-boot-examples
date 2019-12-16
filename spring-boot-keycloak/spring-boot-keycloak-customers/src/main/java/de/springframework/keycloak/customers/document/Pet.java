package de.springframework.keycloak.customers.document;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "pets")
public class Pet extends BaseDocument<Long> {

    @NotNull
    @Field("name")
    private String name;

    @NotNull
    @Field("birth_date")
    private LocalDate birthDate;

    @DBRef
    private PetType type;

    @DBRef
    private Owner owner;

}
