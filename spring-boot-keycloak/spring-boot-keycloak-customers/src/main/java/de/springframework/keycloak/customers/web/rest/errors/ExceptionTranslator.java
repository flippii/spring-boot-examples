package de.springframework.keycloak.customers.web.rest.errors;

import de.springframework.keycloak.customers.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.DefaultProblem;
import org.zalando.problem.Problem;
import org.zalando.problem.ProblemBuilder;
import org.zalando.problem.Status;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.violations.ConstraintViolationProblem;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import java.util.NoSuchElementException;
import java.util.Optional;

import static de.springframework.keycloak.customers.web.rest.errors.ErrorConstants.ERR_CONCURRENCY_FAILURE;
import static de.springframework.keycloak.customers.web.rest.errors.ErrorConstants.ERR_VALIDATION;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionTranslator implements ProblemHandling {

    private static final String MESSAGE_KEY = "message";
    private static final String PATH_KEY = "path";
    private static final String VIOLATIONS_KEY = "violations";

    private final MessageService messageService;

    @Override
    public ResponseEntity<Problem> process(@Nullable ResponseEntity<Problem> entity, NativeWebRequest request) {
        if (entity == null) {
            return entity;
        }

        Problem problem = entity.getBody();
        if (!(problem instanceof ConstraintViolationProblem || problem instanceof DefaultProblem)) {
            return entity;
        }

        ProblemBuilder builder = Problem.builder()
                .withStatus(problem.getStatus())
                .withTitle(problem.getTitle())
                .with(PATH_KEY, Optional.ofNullable(request.getNativeRequest(HttpServletRequest.class))
                        .map(HttpServletRequest::getRequestURI).orElse(""));

        if (problem instanceof ConstraintViolationProblem) {
            builder.with(VIOLATIONS_KEY, ((ConstraintViolationProblem) problem).getViolations())
                    .with(MESSAGE_KEY, translate(ERR_VALIDATION));
        } else {
            builder.withCause(((DefaultProblem) problem).getCause())
                    .withDetail(problem.getDetail())
                    .withInstance(problem.getInstance());

            problem.getParameters().forEach(builder::with);

            if (!problem.getParameters().containsKey(MESSAGE_KEY) && problem.getStatus() != null) {
                builder.with(MESSAGE_KEY, translate("error.http." + problem.getStatus().getStatusCode()));
            }
        }

        return new ResponseEntity<>(builder.build(), entity.getHeaders(), entity.getStatusCode());
    }

    @ExceptionHandler
    public ResponseEntity<Problem> handleNoSuchElementException(NoSuchElementException ex, NativeWebRequest request) {
        Problem problem = Problem.builder()
                .withStatus(Status.NOT_FOUND)
                .with(MESSAGE_KEY, ex.getMessage())
                .build();

        return create(ex, problem, request);
    }

    @ExceptionHandler
    public ResponseEntity<Problem> handleBadRequestException(BadRequestException ex, NativeWebRequest request) {
        Problem problem = Problem.builder()
                .withStatus(ex.getStatus())
                .with(MESSAGE_KEY, translate(ex.getMessage()))
                .build();

        return create(ex, problem, request);
    }

    @ExceptionHandler
    public ResponseEntity<Problem> handleConcurrencyFailure(ConcurrencyFailureException ex, NativeWebRequest request) {
        Problem problem = Problem.builder()
                .withStatus(Status.CONFLICT)
                .with(MESSAGE_KEY, translate(ERR_CONCURRENCY_FAILURE))
                .build();

        return create(ex, problem, request);
    }

    private String translate(String messageId) {
        return messageService.getMessage(messageId);
    }

}
