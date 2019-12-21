package de.springframework.keycloak.credentials.client.configuration;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    @Getter
    public final OauthClient oauthClient = new OauthClient();

    @Data
    public static class OauthClient {

        private String scheme;
        private String host;
        private Integer port;
        private String endpoint;
        private Integer delayBetweenRetries;

    }

}
