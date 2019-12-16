package de.springframework.keycloak.customers.configuration;

import com.mongodb.*;
import de.springframework.keycloak.customers.data.DataInitializer;
import de.springframework.keycloak.customers.data.DataInitializerInvoker;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.core.convert.converter.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.lang.NonNull;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableMongoRepositories(basePackages = "de.springframework.keycloak.customers.repository")
public class DatabaseConfiguration extends AbstractMongoConfiguration  {

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
        return new MongoClient(new MongoClientURI(mongoProperties.getUri(), MongoClientOptions.builder()));
    }

    public static class LocalDateTimeToInstantConverter implements Converter<LocalDateTime, Instant> {

        @Override
        public Instant convert(LocalDateTime source) {
            return source.atZone(ZoneId.systemDefault()).toInstant();
        }

    }
    public static class InstantToLocalDateTimeConverter implements Converter<Instant, LocalDateTime> {

        @Override
        public LocalDateTime convert(@NonNull Instant source) {
            return LocalDateTime.ofInstant(source, ZoneId.systemDefault());
        }

    }

    @Bean
    public DataInitializerInvoker dataInitializerInvoker(List<DataInitializer> initializers) {
        return new DataInitializerInvoker(initializers);
    }

    @Bean
    public GridFsTemplate gridFsTemplate() throws Exception {
        return new GridFsTemplate(mongoDbFactory(), mappingMongoConverter());
    }

}
