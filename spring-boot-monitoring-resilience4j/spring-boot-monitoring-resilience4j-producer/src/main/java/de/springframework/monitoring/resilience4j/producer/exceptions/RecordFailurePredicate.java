package de.springframework.monitoring.resilience4j.producer.exceptions;

import java.util.function.Predicate;

public class RecordFailurePredicate implements Predicate<Throwable> {

    @Override
    public boolean test(Throwable throwable) {
        return !(throwable instanceof BusinessException);
    }

}
