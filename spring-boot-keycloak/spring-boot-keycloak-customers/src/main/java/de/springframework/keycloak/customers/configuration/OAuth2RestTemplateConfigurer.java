package de.springframework.keycloak.customers.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.AllNestedConditions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;

@Slf4j
@Configuration
@Conditional(value = {OAuth2RestTemplateConfigurer.ServiceAccountEnabled.class})
public class OAuth2RestTemplateConfigurer {

    @Bean
    public OAuth2RestTemplate oauth2RestTemplate(OAuth2ProtectedResourceDetails details) {
        OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(details);

        log.debug("Begin OAuth2RestTemplate: getAccessToken");
        /* To validate if required configurations are in place during startup */
        oAuth2RestTemplate.getAccessToken();
        log.debug("End OAuth2RestTemplate: getAccessToken");
        return oAuth2RestTemplate;
    }

    /**
     * Condition class to configure OAuth2RestTemplate when both security is enabled and
     * client credentials property is set for secured micro-service
     * to micro-service call.
     */
    static class ServiceAccountEnabled extends AllNestedConditions {

        ServiceAccountEnabled() {
            super(ConfigurationPhase.PARSE_CONFIGURATION);
        }

        @ConditionalOnProperty(prefix = "application.security", value = "enabled", havingValue = "true")
        static class SecurityEnabled {}

        @ConditionalOnProperty(prefix = "security.oauth2.client", value = "grant-type", havingValue = "client_credentials")
        static class ClientCredentialConfigurationExists {}

    }

}
