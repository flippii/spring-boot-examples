package de.springframework.keycloak.customers.document;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "owners")
public class Owner extends BaseDocument {

    @NotEmpty
    @Field("first_name")
    private String firstName;

    @NotEmpty
    @Field("last_name")
    private String lastName;

    @NotEmpty
    @Field("address")
    private String address;

    @NotEmpty
    @Field("city")
    private String city;

    @NotEmpty
    @Digits(fraction = 0, integer = 10)
    @Field("telephone")
    private String telephone;

}
