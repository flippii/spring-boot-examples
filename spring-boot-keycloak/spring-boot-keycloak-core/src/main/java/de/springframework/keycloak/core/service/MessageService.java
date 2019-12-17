package de.springframework.keycloak.core.service;

public interface MessageService {

    String getMessage(String messageId);

    String getMessage(String messageId, Object[] args);

    String getMessage(String messageId, String defaultMessage);

    String getMessage(String messageId, Object[] args, String defaultMessage);

}
