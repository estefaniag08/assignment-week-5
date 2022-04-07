package dev.applaudostudios.examples.assignmentweek5.common.dto;

import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

public class ErrorDTO implements IEntityDTO{
    private HttpStatus statusCode;
    private String message;
    private Optional<List<?>> errors;

    public ErrorDTO(String message) {
        this.message = message;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Optional<List<?>> getErrors() {
        return errors;
    }

    public void setErrors(List<?> errors) {
        this.errors = Optional.ofNullable(errors);
    }
}
