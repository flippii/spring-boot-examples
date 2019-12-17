package de.springframework.keycloak.core.problem;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.spring.web.advice.AdviceTrait;

import java.util.NoSuchElementException;

import static de.springframework.keycloak.core.problem.ErrorConstants.MESSAGE_KEY;

interface NoSuchElementAdviceTrait extends AdviceTrait {

    @ExceptionHandler
    default ResponseEntity<Problem> handleNoSuchElementException(NoSuchElementException ex, NativeWebRequest request) {
        Problem problem = Problem.builder()
                .withStatus(Status.NOT_FOUND)
                .with(MESSAGE_KEY, ex.getMessage())
                .build();

        return create(ex, problem, request);
    }

}
