package de.springframework.keycloak.customers.document;

import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.math.BigInteger;

abstract class BaseDocument {

    @Id
    private BigInteger id;

    public BigInteger getId() {
        return id;
    }

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
