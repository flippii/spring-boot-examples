package de.springframework.monitoring.backend.data;

import org.springframework.transaction.annotation.Transactional;

public interface DataInitializer {

    @Transactional
    void initialize();

}
