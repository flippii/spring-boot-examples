package de.springframework.keycloak.visits.configuration;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.cors.CorsConfiguration;

import javax.validation.constraints.NotEmpty;

@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    @Getter
    private final Swagger swagger = new Swagger();

    @Getter
    private final CorsConfiguration cors = new CorsConfiguration();

    @Validated
    public static final class Swagger {

        @Getter
        private Version v1 = new Version();

        @Getter
        private Version v2 = new Version();

        @Getter
        private Contact contact = new Contact();

        @Data
        public static final class Version {

            @NotEmpty
            private String title;

            @NotEmpty
            private String description;

            @NotEmpty
            private String version;

            @NotEmpty
            private String groupName;

            @NotEmpty
            private String defaultIncludePattern;

        }

        @Data
        public static final class Contact {

            private String name;
            private String url;
            private String email;

        }

    }

}
