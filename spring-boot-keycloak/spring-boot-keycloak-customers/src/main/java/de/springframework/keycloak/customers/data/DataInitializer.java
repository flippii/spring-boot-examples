package de.springframework.keycloak.customers.data;

import org.springframework.transaction.annotation.Transactional;

public interface DataInitializer {

    @Transactional
    void initialize();

}
