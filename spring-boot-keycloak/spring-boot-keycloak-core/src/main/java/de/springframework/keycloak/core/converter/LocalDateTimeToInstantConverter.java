package de.springframework.keycloak.core.converter;

import org.springframework.core.convert.converter.Converter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class LocalDateTimeToInstantConverter implements Converter<LocalDateTime, Instant> {

    @Override
    public Instant convert(LocalDateTime source) {
        return source.atZone(ZoneId.systemDefault()).toInstant();
    }

}
