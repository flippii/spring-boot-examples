package de.springframework.keycloak.customers.document;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.Digits;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "owners")
public class Owner extends BaseDocument<Long> {

    @NonNull
    @Field("first_name")
    private String firstName;

    @NonNull
    @Field("last_name")
    private String lastName;

    @NonNull
    @Field("address")
    private String address;

    @NonNull
    @Field("city")
    private String city;

    @NonNull
    @Digits(fraction = 0, integer = 10)
    @Field("telephone")
    private String telephone;

}
