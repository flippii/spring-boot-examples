package de.springframework.keycloak.vets.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.*;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static springfox.documentation.builders.PathSelectors.regex;
import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfiguration {

    private final ApplicationProperties applicationProperties;

    @Bean
    public Docket vetsApi10() {
        ApplicationProperties.Swagger.Version version = applicationProperties.getSwagger().getV1();

        return new Docket(SWAGGER_2)
                .groupName(version.getGroupName())
                .select()
                .paths(regex(version.getDefaultIncludePattern()))
                .build()
                .apiInfo(apiInfo(version, applicationProperties.getSwagger().getContact()))
                .globalResponseMessage(RequestMethod.GET, globalResponseMessages());
    }

    private static ApiInfo apiInfo(ApplicationProperties.Swagger.Version version, ApplicationProperties.Swagger.Contact contact) {
        return new ApiInfoBuilder()
                .version(version.getVersion())
                .title(version.getTitle())
                .description(version.getDescription())
                .contact(contact(contact))
                .build();
    }

    private static Contact contact(ApplicationProperties.Swagger.Contact contact) {
        return new Contact(contact.getName(), contact.getUrl(), contact.getEmail());
    }

    private static List<ResponseMessage> globalResponseMessages() {
        return Stream.of(200, 400, 401, 403, 404, 500)
                .map(code ->
                        new ResponseMessageBuilder()
                                .code(code)
                                .message("swagger.api." + code + ".response")
                                .build()
                )
                .collect(Collectors.toList());
    }

}
