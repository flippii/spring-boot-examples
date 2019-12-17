package de.springframework.keycloak.core.swagger.plugin;

import de.springframework.keycloak.core.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.Operation;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.OperationBuilderPlugin;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.swagger.common.SwaggerPluginSupport;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Order(SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER + 1000)
@RequiredArgsConstructor
public class SwaggerOperationBuilderI18nPlugin implements OperationBuilderPlugin {

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
