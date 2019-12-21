package de.springframework.keycloak.credentials.client.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
public class ClientSecurityCustomizer implements WebClientCustomizer {

    private final ServerOAuth2AuthorizedClientExchangeFilterFunction securityExchangeFilterFunction;

    @Override
    public void customize(WebClient.Builder webClientBuilder) {
        webClientBuilder.filters((filterFunctions) -> {
            if (!filterFunctions.contains(securityExchangeFilterFunction)) {
                filterFunctions.add(0, securityExchangeFilterFunction);
            }
        });
    }

}
