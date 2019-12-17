package de.springframework.keycloak.customers.document;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "pet_types")
public class PetType extends BaseDocument {

    @NotEmpty
    @Field("name")
    private String name;

}
