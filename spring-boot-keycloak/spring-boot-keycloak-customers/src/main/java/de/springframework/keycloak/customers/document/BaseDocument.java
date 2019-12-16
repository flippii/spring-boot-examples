package de.springframework.keycloak.customers.document;

import org.springframework.data.annotation.Id;

import java.io.Serializable;

abstract class BaseDocument<PK extends Serializable> implements Serializable {

    @Id
    private PK id;

    public PK getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (this.id == null || obj == null || !(this.getClass().equals(obj.getClass()))) {
            return false;
        }

        BaseDocument<?> that = (BaseDocument<?>) obj;

        return this.id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }

}
