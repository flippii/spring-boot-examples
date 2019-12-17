package de.springframework.keycloak.core.problem;

import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import static de.springframework.keycloak.core.problem.ErrorConstants.ERR_CONCURRENCY_FAILURE;
import static de.springframework.keycloak.core.problem.ErrorConstants.MESSAGE_KEY;

interface ConcurrencyFailureAdviceTrait extends I18nAdviceTrait {

    @ExceptionHandler
    default ResponseEntity<Problem> handleConcurrencyFailure(ConcurrencyFailureException ex, NativeWebRequest request) {
        Problem problem = Problem.builder()
                .withStatus(Status.CONFLICT)
                .with(MESSAGE_KEY, translate(ERR_CONCURRENCY_FAILURE))
                .build();

        return create(ex, problem, request);
    }

}
