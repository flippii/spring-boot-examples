package de.springframework.keycloak.credentials.client;

import de.springframework.keycloak.credentials.client.configuration.ApplicationProperties;
import de.springframework.keycloak.credentials.client.configuration.ClientSecurityConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient.Builder;

@Slf4j
@SpringBootApplication(exclude = {
        ReactiveUserDetailsServiceAutoConfiguration.class
})
@RequiredArgsConstructor
@EnableConfigurationProperties({ ApplicationProperties.class })
public class CredentialsClientApplication implements CommandLineRunner {

    private final ApplicationProperties applicationProperties;
    private final Builder webClientBuilder;

    public static void main(String[] args) {
        SpringApplication.run(CredentialsClientApplication.class, args);
    }

    @Override
    public void run(String... args) {
        webClientBuilder.build()
                .get()
                .uri(uriBuilder ->
                        uriBuilder.scheme(applicationProperties.getOauthClient().getScheme())
                                .host(applicationProperties.getOauthClient().getHost())
                                .port(applicationProperties.getOauthClient().getPort())
                                .path(applicationProperties.getOauthClient().getEndpoint())
                                .build()
                )
                .attributes(ClientSecurityConfiguration.getExchangeFilterWith("keycloak"))
                .accept(MediaType.TEXT_PLAIN)
                .retrieve()
                .bodyToMono(String.class)
                .doOnSubscribe(subscription -> log.info("Connection to security server"))
                .onErrorMap(err -> {
                    try {
                        Thread.sleep(applicationProperties.getOauthClient().getDelayBetweenRetries());
                    } catch (InterruptedException e) {
                        log.warn(e.toString());
                    }
                    return err;
                })
                .subscribe(str -> log.info("Server response : '{}'", str));
    }

}
