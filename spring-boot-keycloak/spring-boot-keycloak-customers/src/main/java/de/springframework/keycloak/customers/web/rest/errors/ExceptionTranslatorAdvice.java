package de.springframework.keycloak.customers.web.rest.errors;

import de.springframework.keycloak.core.service.MessageService;
import de.springframework.keycloak.core.problem.I18nProblemHandling;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionTranslatorAdvice implements I18nProblemHandling {

    private final MessageService messageService;

    @Override
    public String translate(String messageId) {
        return messageService.getMessage(messageId);
    }

}
