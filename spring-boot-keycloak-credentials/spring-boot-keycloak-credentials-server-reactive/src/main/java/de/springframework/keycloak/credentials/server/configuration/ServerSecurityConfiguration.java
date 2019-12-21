package de.springframework.keycloak.credentials.server.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableReactiveMethodSecurity
public class ServerSecurityConfiguration {

    private final ApplicationProperties applicationProperties;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        CustomAuthenticationConverter customJwtAuthConverter = new CustomAuthenticationConverter(
                applicationProperties.getOauth2Security().getIdentityClaimLabel());

        http
                .authorizeExchange()
                    .pathMatchers("/actuator/**")
                        .permitAll()
                    .pathMatchers(applicationProperties.getOauth2Security().getAuthenticatedPathMatchers().toArray(new String[0]))
                        .authenticated()
                .and()
                .oauth2ResourceServer()
                    .jwt()
                        .jwtAuthenticationConverter(customJwtAuthConverter);

        return http.build();
    }

}
