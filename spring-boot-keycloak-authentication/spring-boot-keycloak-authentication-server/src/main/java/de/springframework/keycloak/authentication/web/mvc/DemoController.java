package de.springframework.keycloak.authentication.web.mvc;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.time.LocalDateTime;

import static java.util.Collections.singletonMap;

@Controller
public class DemoController {

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/protected")
    public ModelAndView protectedPage(Principal principal) {
        return new ModelAndView("application", singletonMap("principal", principal));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin")
    public ModelAndView adminPage(Principal principal) {
        return new ModelAndView("admin", singletonMap("principal", principal));
    }

    @GetMapping("/")
    public String unprotectedPage(Model model, Principal principal) {
        model.addAttribute("now", LocalDateTime.now());
        model.addAttribute("principal", principal);
        return "index";
    }

    @GetMapping("/account")
    public String redirectToAccountPage(@AuthenticationPrincipal OAuth2AuthenticationToken authToken) {
        if (authToken == null) {
            return "redirect:/";
        }

        OidcUser user = (OidcUser) authToken.getPrincipal();

        return "redirect:" + user.getIssuer() + "/account?referrer=" + user.getIdToken().getAuthorizedParty();
    }

}
