package de.springframework.monitoring.backend.configuration;

import de.springframework.monitoring.backend.data.DataInitializer;
import de.springframework.monitoring.backend.data.DataInitializerInvoker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;

@Configuration
@EnableJpaRepositories("de.springframework.monitoring.backend.repository")
@EnableTransactionManagement
public class DatabaseConfiguration {

    @Bean
    public DataInitializerInvoker dataInitializerInvoker(List<DataInitializer> initializers) {
        return new DataInitializerInvoker(initializers);
    }

}
