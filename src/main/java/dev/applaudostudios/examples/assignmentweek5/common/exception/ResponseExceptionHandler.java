package dev.applaudostudios.examples.assignmentweek5.common.exception;

import dev.applaudostudios.examples.assignmentweek5.common.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ResponseExceptionHandler {

    @ExceptionHandler(ResponseException.class)
    public ResponseEntity<ErrorDTO> generateResponseException(ResponseException exception){
        ErrorDTO error = new ErrorDTO(exception.getMessage());
        if(exception.getErrors() != null){
            error.setErrors(exception.getErrors());
        }
        if(exception.getStatusCode() != null){
            error.setStatusCode(exception.getStatusCode());
        } else {
            error.setStatusCode(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(error, error.getStatusCode());
    }

    @ExceptionHandler(CrudException.class)
    public ResponseEntity<ErrorDTO> generatePersistenceException(CrudException exception){
        ErrorDTO error = new ErrorDTO(exception.getMessage());
        if(exception.getStatusCode() != null){
            error.setStatusCode(exception.getStatusCode());
        } else {
            error.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(error, error.getStatusCode());
    }
}
