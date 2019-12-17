package de.springframework.keycloak.customers.configuration;

import com.github.cloudyrock.mongock.SpringMongock;
import com.github.cloudyrock.mongock.SpringMongockBuilder;
import com.mongodb.*;
import de.springframework.keycloak.customers.converter.InstantToLocalDateTimeConverter;
import de.springframework.keycloak.customers.converter.LocalDateTimeToInstantConverter;
import de.springframework.keycloak.customers.data.DataInitializer;
import de.springframework.keycloak.customers.data.DataInitializerInvoker;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.lang.NonNull;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableMongoRepositories(basePackages = "de.springframework.keycloak.customers.repository")
public class DatabaseConfiguration extends AbstractMongoConfiguration  {

    private static final String CHANGESETS = "de.springframework.keycloak.customers.changesets";

    private final MongoProperties mongoProperties;
    private final LocalValidatorFactoryBean validator;

    @Bean
    public ValidatingMongoEventListener validatingMongoEventListener() {
        return new ValidatingMongoEventListener(validator);
    }

    @Override
    protected @NonNull String getDatabaseName() {
        return mongoProperties.getDatabase();
    }

    @Override
    public @NonNull CustomConversions customConversions() {
        return new MongoCustomConversions(List.of(
                new LocalDateTimeToInstantConverter(),
                new InstantToLocalDateTimeConverter()
        ));
    }

    @Override
    public @NonNull MongoClient mongoClient() {
        return new MongoClient(new MongoClientURI(mongoProperties.getUri(), MongoClientOptions.builder()
                .maxConnectionIdleTime(60000)));
    }

    @Bean
    public DataInitializerInvoker dataInitializerInvoker(List<DataInitializer> initializers) {
        return new DataInitializerInvoker(initializers);
    }

    @Bean
    public GridFsTemplate gridFsTemplate() throws Exception {
        return new GridFsTemplate(mongoDbFactory(), mappingMongoConverter());
    }

    @Bean
    public MongoTransactionManager transactionManager(MongoDbFactory dbFactory) {
        return new MongoTransactionManager(dbFactory);
    }

    @Bean
    public SpringMongock mongock(Environment environment) {
        return new SpringMongockBuilder(mongoClient(), mongoProperties.getDatabase(), CHANGESETS)
                .setSpringEnvironment(environment)
                .setLockQuickConfig()
                .setEnabled(true)
                .build();
    }

}
