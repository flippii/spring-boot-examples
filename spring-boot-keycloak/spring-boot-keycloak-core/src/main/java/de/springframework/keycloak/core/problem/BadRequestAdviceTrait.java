package de.springframework.keycloak.core.problem;

import de.springframework.keycloak.core.exception.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Problem;

import static de.springframework.keycloak.core.problem.ErrorConstants.MESSAGE_KEY;

interface BadRequestAdviceTrait extends I18nAdviceTrait {

    @ExceptionHandler
    default ResponseEntity<Problem> handleBadRequestException(BadRequestException ex, NativeWebRequest request) {
        Problem problem = Problem.builder()
                .withStatus(ex.getStatus())
                .with(MESSAGE_KEY, translate(ex.getMessage()))
                .build();

        return create(ex, problem, request);
    }

}
