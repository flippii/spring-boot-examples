package de.springframework.monitoring.resilience4j.producer.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories("de.springframework.monitoring.resilience4j.producer.repository")
@EnableTransactionManagement
public class DatabaseConfiguration {
}
