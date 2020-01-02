package de.springframework.monitoring.resilience4j.producer.entity;

import lombok.Getter;
import org.springframework.data.util.ProxyUtils;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@MappedSuperclass
abstract class BaseEntity<PK extends Serializable> implements Serializable {

    private static final long serialVersionUID = -5554308939380869754L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private PK id;

    @Transient
    public boolean isNew() {
        return null == getId();
    }

    @Override
    public String toString() {
        return String.format("Entity of type %s with id: %s", getClass().getName(), getId());
    }

    @Override
    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        if (!getClass().equals(ProxyUtils.getUserClass(obj))) {
            return false;
        }

        BaseEntity<?> that = (BaseEntity<?>) obj;

        return null != getId() && getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode += null == getId() ? 0 : getId().hashCode() * 31;
        return hashCode;
    }

}
