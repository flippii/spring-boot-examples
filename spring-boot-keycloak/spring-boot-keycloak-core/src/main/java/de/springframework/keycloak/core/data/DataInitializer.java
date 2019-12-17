package de.springframework.keycloak.core.data;

import org.springframework.transaction.annotation.Transactional;

public interface DataInitializer {

    @Transactional
    void initialize();

}
