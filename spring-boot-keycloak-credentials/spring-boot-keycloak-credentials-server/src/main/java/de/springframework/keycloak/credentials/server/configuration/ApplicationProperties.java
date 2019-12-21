package de.springframework.keycloak.credentials.server.configuration;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    @Getter
    public final Oauth2Security oauth2Security = new Oauth2Security();

    @Data
    public static class Oauth2Security {

        private List<String> authenticatedPathMatchers = new ArrayList<>();
        private String identityClaimLabel;

    }

}
