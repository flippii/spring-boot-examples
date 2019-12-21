package de.springframework.keycloak.credentials.server.configuration;

import com.nimbusds.jwt.JWTClaimsSet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationConverter implements Converter<Jwt, Mono<AbstractAuthenticationToken>> {

    private static final String ANONYMOUS = "anonymous";

    private final String identityClaimLabel;

    @Override
    public Mono<AbstractAuthenticationToken> convert(Jwt source) {
        return Mono.justOrEmpty(doConversion(source));
    }

    private AbstractAuthenticationToken doConversion(Jwt token) {
        Map<String, Object> claimsSet = token.getClaims();

        String identity = (String) claimsSet.get(identityClaimLabel);

        if (StringUtils.hasText(identity)) {
            JWTClaimsSet.Builder jwtClaimsSetBuilder = new JWTClaimsSet.Builder();
            claimsSet.forEach(jwtClaimsSetBuilder::claim);
            JWTClaimsSet jwtClaimsSet = jwtClaimsSetBuilder.build();

            return new JwtAuthentication(identity, jwtClaimsSet, null);
        } else {
            log.error("Missing '{}' field or value is not valid in the JWT token", identityClaimLabel);
            return getNonAuthenticatedToken();
        }
    }

    private static AbstractAuthenticationToken getNonAuthenticatedToken() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(ANONYMOUS));
        AnonymousAuthenticationToken authenticationToken = new AnonymousAuthenticationToken(ANONYMOUS, ANONYMOUS, authorities);
        authenticationToken.setAuthenticated(false);
        return authenticationToken;
    }

    private static class JwtAuthentication extends AbstractAuthenticationToken {

        private final transient Object principal;
        private JWTClaimsSet jwtClaimsSet;

        public JwtAuthentication(Object principal, JWTClaimsSet jwtClaimsSet,
                                 Collection<? extends GrantedAuthority> authorities) {

            super(authorities);
            this.principal = principal;
            this.jwtClaimsSet = jwtClaimsSet;
            super.setAuthenticated(true);
        }

        @Override
        public Object getCredentials() {
            return null;
        }

        @Override
        public Object getPrincipal() {
            return principal;
        }

        public JWTClaimsSet getJwtClaimsSet() {
            return jwtClaimsSet;
        }

    }

}
