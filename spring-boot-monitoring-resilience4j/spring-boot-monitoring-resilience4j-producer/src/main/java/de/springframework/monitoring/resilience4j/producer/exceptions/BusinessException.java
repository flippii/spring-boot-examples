package de.springframework.monitoring.resilience4j.producer.exceptions;

public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }

}
