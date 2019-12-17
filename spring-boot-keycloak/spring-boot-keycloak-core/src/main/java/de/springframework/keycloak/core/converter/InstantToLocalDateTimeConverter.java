package de.springframework.keycloak.core.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class InstantToLocalDateTimeConverter implements Converter<Instant, LocalDateTime> {

    @Override
    public LocalDateTime convert(@NonNull Instant source) {
        return LocalDateTime.ofInstant(source, ZoneId.systemDefault());
    }

}
