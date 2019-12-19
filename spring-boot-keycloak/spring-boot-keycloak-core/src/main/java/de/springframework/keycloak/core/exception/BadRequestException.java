package de.springframework.keycloak.core.exception;

import lombok.Getter;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.util.Collections;
import java.util.List;

@Getter
public class BadRequestException extends AbstractThrowableProblem {

    private Object[] args;

    public BadRequestException(String messageId) {
        this(messageId, Collections.emptyList());
    }

    public BadRequestException(String messageId, List<Object> args) {
        super(null, messageId, Status.BAD_REQUEST);
        this.args = args.toArray();
    }

}
