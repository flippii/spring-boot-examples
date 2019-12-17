package de.springframework.keycloak.customers.configuration;

import com.github.cloudyrock.mongock.SpringMongock;
import com.github.cloudyrock.mongock.SpringMongockBuilder;
import de.springframework.keycloak.core.configuration.BaseMongoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "de.springframework.keycloak.customers.repository")
public class MongoConfiguration extends BaseMongoConfiguration {

    private static final String CHANGESETS = "de.springframework.keycloak.customers.changesets";

    public MongoConfiguration(MongoProperties mongoProperties) {
        super(mongoProperties);
    }

    @Bean
    public SpringMongock mongock(Environment environment) {
        return new SpringMongockBuilder(mongoClient(), getDatabaseName(), CHANGESETS)
                .setSpringEnvironment(environment)
                .setLockQuickConfig()
                .setEnabled(true)
                .build();
    }

}
