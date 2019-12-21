package de.springframework.keycloak.authentication.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpRequest;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;

@Slf4j
@RequiredArgsConstructor
public class BearerTokenInterceptor implements ClientHttpRequestInterceptor {

    private final Duration accessTokenExpiresSkew = Duration.ofMinutes(1);
    private final Clock clock = Clock.systemUTC();

    private final OAuth2AuthorizedClientService oAuth2AuthorizedClientService;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        OAuth2AuthenticationToken currentUser = (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        OAuth2AuthorizedClient currentUserClientConfig = clientConfig(currentUser);

        if (isExpired(currentUserClientConfig.getAccessToken())) {
            log.info("AccessToken expired, refreshing automatically");
            refreshToken(currentUserClientConfig, currentUser);
        }

        request.getHeaders().add(AUTHORIZATION, "Bearer " + currentUserClientConfig.getAccessToken().getTokenValue());

        return execution.execute(request, body);
    }

    private OAuth2AuthorizedClient clientConfig(OAuth2AuthenticationToken currentUser) {
        OAuth2AuthorizedClient oAuth2AuthorizedClient = oAuth2AuthorizedClientService.loadAuthorizedClient(
                currentUser.getAuthorizedClientRegistrationId(), currentUser.getName());

        return Optional.ofNullable(oAuth2AuthorizedClient)
                .orElseThrow(() -> new CredentialsExpiredException("could not load client config for $name, reauthenticate"));
    }

    private void refreshToken(OAuth2AuthorizedClient currentClient, OAuth2AuthenticationToken currentUser) {
        OAuth2AccessTokenResponse atr = refreshTokenClient(currentClient);
        if (atr == null || atr.getAccessToken() == null) {
            log.info("Failed to refresh token for ${currentUser.name}");
            return;
        }

        OAuth2RefreshToken refreshToken = Optional.ofNullable(currentClient.getRefreshToken())
                .orElse(new OAuth2RefreshToken(currentClient.getAccessToken().getTokenValue(), currentClient.getAccessToken().getExpiresAt()));

        OAuth2AuthorizedClient updatedClient = new OAuth2AuthorizedClient(
                currentClient.getClientRegistration(),
                currentClient.getPrincipalName(),
                atr.getAccessToken(),
                refreshToken
        );

        oAuth2AuthorizedClientService.saveAuthorizedClient(updatedClient, currentUser);
    }

    private OAuth2AccessTokenResponse refreshTokenClient(OAuth2AuthorizedClient currentClient) {
        String refreshToken = currentClient.getRefreshToken() == null ? "" : currentClient.getRefreshToken().getTokenValue();

        MultiValueMap<String, String> formParameters = new LinkedMultiValueMap<>();
        formParameters.add(OAuth2ParameterNames.GRANT_TYPE, AuthorizationGrantType.REFRESH_TOKEN.getValue());
        formParameters.add(OAuth2ParameterNames.REFRESH_TOKEN, refreshToken);
        formParameters.add(OAuth2ParameterNames.REDIRECT_URI, currentClient.getClientRegistration().getRedirectUriTemplate());

        RequestEntity<?> requestEntity = RequestEntity.post(URI.create(currentClient.getClientRegistration().getProviderDetails().getTokenUri()))
                .header(CONTENT_TYPE, APPLICATION_FORM_URLENCODED_VALUE)
                .body(formParameters);

        try {
            RestTemplate restTemplate = restTemplate(currentClient.getClientRegistration().getClientId(),
                    currentClient.getClientRegistration().getClientSecret());

            ResponseEntity<OAuth2AccessTokenResponse> responseEntity = restTemplate.exchange(requestEntity,
                    OAuth2AccessTokenResponse.class);

            return responseEntity.getBody();
        } catch (OAuth2AuthorizationException ex) {
            log.error("Unable to refresh token ${e.error.errorCode}");
            throw new OAuth2AuthenticationException(ex.getError(), ex);
        }
    }

    private boolean isExpired(OAuth2AccessToken accessToken) {
        Instant now = clock.instant();
        Instant expiresAt = Optional.ofNullable(accessToken.getExpiresAt()).orElse(Instant.now());
        return now.isAfter(expiresAt.minus(accessTokenExpiresSkew));
    }

    private RestTemplate restTemplate(String clientId, String clientSecret) {
        return new RestTemplateBuilder()
                .additionalMessageConverters(
                        new FormHttpMessageConverter(),
                        new OAuth2AccessTokenResponseHttpMessageConverter())
                .errorHandler(new OAuth2ErrorResponseErrorHandler())
                .basicAuthentication(clientId, clientSecret)
                .build();
    }

}
