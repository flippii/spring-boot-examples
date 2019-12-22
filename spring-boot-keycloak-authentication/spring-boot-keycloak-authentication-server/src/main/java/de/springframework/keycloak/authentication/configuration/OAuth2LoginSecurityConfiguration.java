package de.springframework.keycloak.authentication.configuration;

import de.springframework.keycloak.authentication.security.KeycloakLogoutHandler;
import de.springframework.keycloak.authentication.security.KeycloakOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter.DEFAULT_AUTHORIZATION_REQUEST_BASE_URI;

@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class OAuth2LoginSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final ApplicationProperties applicationProperties;
    private final OAuth2ClientProperties oauth2ClientProperties;
    private final RestTemplateBuilder restTemplateBuilder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                    .authorizeRequests().anyRequest().permitAll()
                .and()
                    .logout().addLogoutHandler(logoutHandler())
                .and()
                    .oauth2Login()
                        .userInfoEndpoint().oidcUserService(oidcUserService()).userAuthoritiesMapper(authoritiesMapper())
                .and()
                    .loginPage(DEFAULT_AUTHORIZATION_REQUEST_BASE_URI + "/" + applicationProperties.getOauth2Client().getRealm());
    }

    @Bean
    public OidcUserService oidcUserService() {
        return new KeycloakOauth2UserService(jwtDecoder(), oauth2ClientProperties);
    }

    @Bean
    public LogoutHandler logoutHandler() {
        return new KeycloakLogoutHandler(restTemplateBuilder);
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withJwkSetUri(oauth2ClientProperties.getProvider().get("keycloak").getJwkSetUri()).build();
    }

    @Bean
    public GrantedAuthoritiesMapper authoritiesMapper() {
        SimpleAuthorityMapper authoritiesMapper = new SimpleAuthorityMapper();
        authoritiesMapper.setConvertToUpperCase(true);
        return authoritiesMapper;
    }

}
