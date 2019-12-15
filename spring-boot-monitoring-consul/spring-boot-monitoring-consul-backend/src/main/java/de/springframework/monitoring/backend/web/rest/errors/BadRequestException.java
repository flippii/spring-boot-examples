package de.springframework.monitoring.backend.web.rest.errors;

import lombok.Getter;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.util.List;

@Getter
public class BadRequestException extends AbstractThrowableProblem {

    private Object[] args;

    public BadRequestException(String messageId) {
        this(messageId, List.of());
    }

    public BadRequestException(String messageId, List<Object> args) {
        super(null, messageId, Status.BAD_REQUEST);
        this.args = args.toArray();
    }

}
