package de.springframework.keycloak.core.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public abstract class BaseDocument {

    @Id
    private String id;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (id == null || obj == null || !(getClass().equals(obj.getClass()))) {
            return false;
        }

        BaseDocument that = (BaseDocument) obj;

        return id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }

}
