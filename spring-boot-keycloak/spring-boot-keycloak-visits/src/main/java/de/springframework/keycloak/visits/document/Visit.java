package de.springframework.keycloak.visits.document;

import de.springframework.keycloak.core.document.BaseDocument;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Document(collection = "visits")
public class Visit extends BaseDocument {

    @NotNull
    @Field("visit_date")
    private LocalDate date;

    @NotEmpty
    @Field("description")
    private String description;

    @NotEmpty
    @Field("pet_id")
    private String petId;

    public Visit() {
        this.date = LocalDate.now();
    }

}
