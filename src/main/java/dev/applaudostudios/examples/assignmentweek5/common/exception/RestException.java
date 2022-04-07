package dev.applaudostudios.examples.assignmentweek5.common.exception;

import org.springframework.http.HttpStatus;

public interface RestException {
    public HttpStatus getStatusCode();
}
