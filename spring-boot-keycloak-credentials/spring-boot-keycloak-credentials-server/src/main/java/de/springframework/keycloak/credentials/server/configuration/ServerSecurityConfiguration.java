package de.springframework.keycloak.credentials.server.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class ServerSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final ApplicationProperties applicationProperties;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationConverter customJwtAuthConverter = new CustomAuthenticationConverter(
                applicationProperties.getOauth2Security().getIdentityClaimLabel());

        http
                .authorizeRequests()
                    .antMatchers("/actuator/**")
                        .permitAll()
                    .antMatchers(applicationProperties.getOauth2Security().getAuthenticatedPathMatchers().toArray(new String[0]))
                        .authenticated()
                .and()
                    .oauth2ResourceServer()
                    .jwt()
                        .jwtAuthenticationConverter(customJwtAuthConverter);
    }

}
