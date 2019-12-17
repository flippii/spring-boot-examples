package de.springframework.keycloak.core.swagger.plugin;

import de.springframework.keycloak.core.service.MessageService;
import io.swagger.annotations.ApiModelProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;
import springfox.documentation.swagger.common.SwaggerPluginSupport;

import java.util.Optional;

@Order(SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER + 1000)
@RequiredArgsConstructor
public class SwaggerModelPropertyBuilderI18nPlugin implements ModelPropertyBuilderPlugin {

    private final MessageService messageService;

    @Override
    public boolean supports(DocumentationType delimiter) {
        return true;
    }

    @Override
    public void apply(ModelPropertyContext context) {
        Optional<ApiModelProperty> annotation = Optional.empty();

        if (context.getBeanPropertyDefinition().isPresent()) {
            annotation = Optional.ofNullable(context.getBeanPropertyDefinition().get().getGetter().getAnnotation(ApiModelProperty.class));
        }

        annotation.ifPresent(apiModelProperty ->
                context.getBuilder()
                        .description(messageService.getMessage(apiModelProperty.value(), apiModelProperty.value())));
    }

}
