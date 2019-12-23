package de.springframework.monitoring.hystrix.producer.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories("de.springframework.monitoring.hystrix.producer.repository")
@EnableTransactionManagement
public class DatabaseConfiguration {
}
