package de.springframework.monitoring.backend.web.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class BadRequestException extends AbstractThrowableProblem {

    public BadRequestException(String messageId) {
        super(null, messageId, Status.BAD_REQUEST);
    }

}
