package dev.applaudostudios.examples.assignmentweek5.common.exception;


public class EmailExistsException extends RuntimeException{

    public EmailExistsException(final String message){
        super(message);
    }
}
