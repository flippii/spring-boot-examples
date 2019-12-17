package de.springframework.keycloak.core.configuration;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import de.springframework.keycloak.core.converter.InstantToLocalDateTimeConverter;
import de.springframework.keycloak.core.converter.LocalDateTimeToInstantConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.lang.NonNull;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.List;

@RequiredArgsConstructor
public abstract class BaseMongoConfiguration extends AbstractMongoConfiguration {

    private final MongoProperties mongoProperties;

    @Bean
    public ValidatingMongoEventListener validatingMongoEventListener(LocalValidatorFactoryBean validator) {
        return new ValidatingMongoEventListener(validator);
    }

    @NonNull
    @Override
    protected String getDatabaseName() {
        return mongoProperties.getDatabase();
    }

    @NonNull
    @Override
    public CustomConversions customConversions() {
        return new MongoCustomConversions(List.of(
                new LocalDateTimeToInstantConverter(),
                new InstantToLocalDateTimeConverter()
        ));
    }

    @NonNull
    @Override
    public MongoClient mongoClient() {
        return new MongoClient(new MongoClientURI(mongoProperties.getUri(), MongoClientOptions.builder()
                .maxConnectionIdleTime(60000)));
    }

    @Bean
    public GridFsTemplate gridFsTemplate(MongoDbFactory mongoDbFactory, MongoConverter mappingMongoConverter) {
        return new GridFsTemplate(mongoDbFactory, mappingMongoConverter);
    }

    @Bean
    public MongoTransactionManager transactionManager(MongoDbFactory dbFactory) {
        return new MongoTransactionManager(dbFactory);
    }

}
