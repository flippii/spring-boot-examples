package de.springframework.keycloak.authentication.security;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.util.CollectionUtils;

import java.util.*;

@RequiredArgsConstructor
public class KeycloakOauth2UserService extends OidcUserService {

    private final JwtDecoder jwtDecoder;
    private final OAuth2ClientProperties oauth2ClientProperties;

    /**
     * Augments {@link OidcUserService#loadUser(OidcUserRequest)} to add authorities provided by Keycloak.
     *
     * Needed because {@link OidcUserService#loadUser(OidcUserRequest)} (currently) does not provide a hook for
     * adding custom authorities from a {@link OidcUserRequest}.
     */
    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser user = super.loadUser(userRequest);

        Set<GrantedAuthority> authorities = new LinkedHashSet<>();
        authorities.addAll(user.getAuthorities());
        authorities.addAll(extractAuthorities(userRequest));

        return new DefaultOidcUser(authorities, userRequest.getIdToken(), user.getUserInfo(),
                oauth2ClientProperties.getProvider().get("keycloak").getUserNameAttribute());
    }

    private Collection<? extends GrantedAuthority> extractAuthorities(OidcUserRequest userRequest) {
        Jwt token = parseJwt(userRequest.getAccessToken().getTokenValue());
        ClientRegistration registration = userRequest.getClientRegistration();
        List<String> clientRoles = extractRoles(token.getClaims(), registration.getClientId());
        return AuthorityUtils.createAuthorityList(clientRoles.toArray(new String[0]));
    }

    @SuppressWarnings("unchecked")
    private List<String> extractRoles(Map<String, Object> claims, String clientId) {
        Map<String, Object> resourceMap = (Map<String, Object>) claims.get("resource_access");
        Map<String, Map<String, Object>> clientResource = (Map<String, Map<String, Object>>) resourceMap.get(clientId);

        if (CollectionUtils.isEmpty(clientResource)) {
            return Collections.emptyList();
        }

        return (List<String>) clientResource.get("roles");
    }

    private Jwt parseJwt(String accessTokenValue) {
        try {
            // Token is already verified by spring security infrastructure
            return jwtDecoder.decode(accessTokenValue);
        } catch (JwtException e) {
            throw new OAuth2AuthenticationException(new OAuth2Error(OAuth2ErrorCodes.INVALID_REQUEST), e);
        }
    }

}
