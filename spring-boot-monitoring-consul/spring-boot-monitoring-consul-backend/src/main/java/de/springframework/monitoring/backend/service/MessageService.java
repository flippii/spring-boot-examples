package de.springframework.monitoring.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageSource messageSource;

    public String getMessage(String messageId) {
        return getMessage(messageId, "Translate: [" + messageId + "]");
    }

    public String getMessage(String messageId, Object[] args) {
        return getMessage(messageId, args, "Translate: [" + messageId + "]");
    }

    public String getMessage(String messageId, String defaultMessage) {
        return getMessage(messageId, null, defaultMessage);
    }

    public String getMessage(String messageId, Object[] args, String defaultMessage) {
        return messageSource.getMessage(messageId, args, defaultMessage, getLocale());
    }

    private Locale getLocale() {
        return LocaleContextHolder.getLocale();
    }



}
