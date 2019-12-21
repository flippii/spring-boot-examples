package de.springframework.keycloak.authentication.configuration;

import de.springframework.keycloak.authentication.security.KeycloakLogoutHandler;
import de.springframework.keycloak.authentication.security.KeycloakOauth2UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
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
import org.springframework.security.oauth2.jwt.NimbusJwtDecoderJwkSupport;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.client.RestTemplate;

import static org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter.DEFAULT_AUTHORIZATION_REQUEST_BASE_URI;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class OAuth2LoginSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final String realm;
    private final OAuth2ClientProperties oauth2ClientProperties;

    public OAuth2LoginSecurityConfiguration(@Value("${kc.realm}")  String realm,
                                            OAuth2ClientProperties oauth2ClientProperties) {

        this.realm = realm;
        this.oauth2ClientProperties = oauth2ClientProperties;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).and()
                // Depends on your taste. You can configure single paths here
                // or allow everything a I did and then use method based security
                // like in the controller below
                .authorizeRequests().anyRequest().permitAll().and()
                // Propagate logouts via /logout to Keycloak
                .logout().addLogoutHandler(logoutHandler()).and()
                // This is the point where OAuth2 login of Spring 5 gets enabled
                .oauth2Login().userInfoEndpoint().oidcUserService(oidcUserService()).and()
                // I don't want a page with different clients as login options
                // So i use the constant from OAuth2AuthorizationRequestRedirectFilter
                // plus the configured realm as immediate redirect to Keycloak
                .loginPage(DEFAULT_AUTHORIZATION_REQUEST_BASE_URI + "/" + realm);
    }

    @Bean
    public OidcUserService oidcUserService() {
        return new KeycloakOauth2UserService(jwtDecoder(), authoritiesMapper());
    }

    @Bean
    public LogoutHandler logoutHandler() {
        return new KeycloakLogoutHandler(new RestTemplate());
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return new NimbusJwtDecoderJwkSupport(oauth2ClientProperties.getProvider().get("keycloak").getJwkSetUri());
    }

    @Bean
    public GrantedAuthoritiesMapper authoritiesMapper() {
        SimpleAuthorityMapper authoritiesMapper = new SimpleAuthorityMapper();
        authoritiesMapper.setConvertToUpperCase(true);
        return authoritiesMapper;
    }

}
