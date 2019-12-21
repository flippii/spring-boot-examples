package de.springframework.keycloak.credentials.client.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.client.web.server.UnAuthenticatedServerOAuth2AuthorizedClientRepository;

import java.util.Map;
import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
public class ClientSecurityConfiguration {

    @Bean
    public ClientSecurityCustomizer webClientSecurityCustomizer(
            ReactiveClientRegistrationRepository clientRegistrations) {

        // Provides support for an unauthenticated user such as an application
        ServerOAuth2AuthorizedClientExchangeFilterFunction oauth = new ServerOAuth2AuthorizedClientExchangeFilterFunction(
                clientRegistrations, new UnAuthenticatedServerOAuth2AuthorizedClientRepository());

        // Build up a new WebClientCustomizer implementation to inject the oauth filter
        // function into the WebClient.Builder instance
        return new ClientSecurityCustomizer(oauth);
    }

    /**
     * Helper function to include the Spring CLIENT_REGISTRATION_ID_ATTR_NAME in a
     * properties Map
     *
     * @param provider - OAuth2 authorization provider name
     * @return consumer properties Map
     */
    public static Consumer<Map<String, Object>> getExchangeFilterWith(String provider) {
        return ServerOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId(provider);
    }

}
