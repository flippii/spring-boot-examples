package de.springframework.keycloak.core.configuration;

import de.springframework.keycloak.core.data.DataInitializer;
import de.springframework.keycloak.core.data.DataInitializerInvoker;
import de.springframework.keycloak.core.service.DefaultMessageService;
import de.springframework.keycloak.core.service.MessageService;
import de.springframework.keycloak.core.swagger.plugin.SwaggerModelPropertyBuilderI18nPlugin;
import de.springframework.keycloak.core.swagger.plugin.SwaggerOperationBuilderI18nPlugin;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public class CoreModuleConfiguration {

    @Bean
    public MessageService messageService(MessageSource messageSource) {
        return new DefaultMessageService(messageSource);
    }

    @Bean
    public ApplicationRunner dataInitializerInvoker(ObjectProvider<List<DataInitializer>> provider) {
        return new DataInitializerInvoker(provider.getIfAvailable(Collections::emptyList));
    }

    @Bean
    public SwaggerOperationBuilderI18nPlugin swaggerOperationBuilderI18n(MessageService messageService) {
        return new SwaggerOperationBuilderI18nPlugin(messageService);
    }

    @Bean
    public SwaggerModelPropertyBuilderI18nPlugin swaggerModelPropertyBuilderI18n(MessageService messageService) {
        return new SwaggerModelPropertyBuilderI18nPlugin(messageService);
    }

}
