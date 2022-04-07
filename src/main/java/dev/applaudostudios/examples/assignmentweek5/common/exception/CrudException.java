package dev.applaudostudios.examples.assignmentweek5.common.exception;

import org.springframework.http.HttpStatus;

import javax.persistence.PersistenceException;

public class CrudException extends PersistenceException implements RestException{
    private HttpStatus statusCode;

    public CrudException( String message){
        super(message);
    }

    public CrudException(String message, HttpStatus statusCode){
        this(message);
        this.statusCode = statusCode;
    }

    @Override
    public HttpStatus getStatusCode() {
        return null;
    }
}
