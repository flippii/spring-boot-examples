package de.springframework.keycloak.customers.configuration;

import de.springframework.keycloak.customers.service.MessageService;
import io.swagger.annotations.ApiModelProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;
import springfox.documentation.spi.service.OperationBuilderPlugin;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.common.SwaggerPluginSupport;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static springfox.documentation.builders.PathSelectors.regex;
import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

@Configuration
@EnableSwagger2
@RequiredArgsConstructor
public class SwaggerConfiguration {

    private final MessageService messageService;
    private final ApplicationProperties applicationProperties;

    @Bean
    public Docket swaggerPetClinitApi10() {
        ApplicationProperties.Swagger.Version version = applicationProperties.getSwagger().getV1();

        return new Docket(SWAGGER_2)
                .groupName(version.getGroupName())
                .select()
                .paths(regex(version.getDefaultIncludePattern()))
                .build()
                .apiInfo(apiInfo(version, applicationProperties.getSwagger().getContact()))
                .globalResponseMessage(RequestMethod.GET, globalResponseMessages());
    }

    private ApiInfo apiInfo(ApplicationProperties.Swagger.Version version, ApplicationProperties.Swagger.Contact contact) {
        return new ApiInfoBuilder()
                .version(version.getVersion())
                .title(version.getTitle())
                .description(version.getDescription())
                .contact(contact(contact))
                .build();
    }

    private Contact contact(ApplicationProperties.Swagger.Contact contact) {
        return new Contact(contact.getName(), contact.getUrl(), contact.getEmail());
    }

    private List<ResponseMessage> globalResponseMessages() {
        return Stream.of(200, 400, 401, 403, 404, 500)
                .map(code ->
                        new ResponseMessageBuilder()
                                .code(code)
                                .message("swagger.api." + code + ".response")
                                .build()
                )
                .collect(Collectors.toList());
    }

    @Bean
    public SwaggerOperationBuilderI18nPlugin swaggerOperationBuilderI18n() {
        return new SwaggerOperationBuilderI18nPlugin(messageService);
    }

    @Bean
    public SwaggerModelPropertyBuilderI18nPlugin swaggerModelPropertyBuilderI18n() {
        return new SwaggerModelPropertyBuilderI18nPlugin(messageService);
    }

    @Order(SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER + 1000)
    @RequiredArgsConstructor
    public static class SwaggerModelPropertyBuilderI18nPlugin implements ModelPropertyBuilderPlugin {

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

    @Order(SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER + 1000)
    @RequiredArgsConstructor
    public static class SwaggerOperationBuilderI18nPlugin implements OperationBuilderPlugin {

        private final MessageService messageService;

        @Override
        public boolean supports(DocumentationType delimiter) {
            return true;
        }

        @Override
        public void apply(OperationContext context) {
            Operation operation = context.operationBuilder().build();

            context.operationBuilder()
                    .summary(messageService.getMessage(operation.getSummary(), operation.getSummary()))
                    .parameters(translateParameters(operation.getParameters()))
                    .responseMessages(translateResponseMessages(operation.getResponseMessages()));
        }

        private List<Parameter> translateParameters(List<Parameter> parameters) {
            return parameters.stream()
                    .map(this::createParameter)
                    .collect(Collectors.toList());
        }

        private Parameter createParameter(Parameter parameterOld) {
            return new ParameterBuilder()
                    .name(parameterOld.getName())
                    .allowableValues(parameterOld.getAllowableValues())
                    .allowMultiple(parameterOld.isAllowMultiple())
                    .defaultValue(parameterOld.getDefaultValue())
                    .description(messageService.getMessage(parameterOld.getDescription(), parameterOld.getDescription()))
                    .modelRef(parameterOld.getModelRef())
                    .parameterAccess(parameterOld.getParamAccess())
                    .parameterType(parameterOld.getParamType())
                    .required(parameterOld.isRequired())
                    .type(parameterOld.getType().orNull())
                    .hidden(parameterOld.isHidden())
                    .allowEmptyValue(parameterOld.isAllowEmptyValue())
                    .order(parameterOld.getOrder())
                    .vendorExtensions(parameterOld.getVendorExtentions())
                    .build();
        }

        private Set<ResponseMessage> translateResponseMessages(Set<ResponseMessage> messages) {
            return messages.stream()
                    .map(this::createResponseMessage)
                    .collect(Collectors.toSet());
        }

        private ResponseMessage createResponseMessage(ResponseMessage responseMessageOld) {
            return new ResponseMessageBuilder()
                    .code(responseMessageOld.getCode())
                    .message(messageService.getMessage(responseMessageOld.getMessage(), responseMessageOld.getMessage()))
                    .responseModel(responseMessageOld.getResponseModel())
                    .headersWithDescription(responseMessageOld.getHeaders())
                    .vendorExtensions(responseMessageOld.getVendorExtensions())
                    .build();
        }

    }

}
