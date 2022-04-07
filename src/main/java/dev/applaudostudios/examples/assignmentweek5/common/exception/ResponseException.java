package dev.applaudostudios.examples.assignmentweek5.common.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public class ResponseException extends RuntimeException implements RestException{
    private List<?> errors;
    private HttpStatus statusCode;

    public ResponseException(String message){
        super(message);
    }
    public ResponseException(String message, List<?> errors, HttpStatus statusCode){
        this(message);
        this.errors = errors;
        this.statusCode = statusCode;
    }

    public List<?> getErrors() {
        return errors;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }
}
