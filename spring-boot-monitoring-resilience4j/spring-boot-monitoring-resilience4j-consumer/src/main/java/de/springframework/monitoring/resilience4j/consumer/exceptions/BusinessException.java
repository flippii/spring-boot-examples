package de.springframework.monitoring.resilience4j.consumer.exceptions;

public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }

}
