package de.springframework.keycloak.authentication.configuration;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    @Getter
    public final OAuth2Client oauth2Client = new OAuth2Client();

    @Data
    public static final class OAuth2Client {

        private String baseUrl;
        private String realm;
        private String realmUrl;

    }

}
