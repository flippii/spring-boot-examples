package de.springframework.keycloak.core.problem;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class ErrorConstants {

    static final String MESSAGE_KEY = "message";
    static final String PATH_KEY = "path";
    static final String VIOLATIONS_KEY = "violations";

    static final String ERR_CONCURRENCY_FAILURE = "error.concurrencyFailure";
    static final String ERR_VALIDATION = "error.validation";

}
