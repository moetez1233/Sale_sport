package com.app.FirstApp.config.customException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler
    public ResponseEntity<ErrorHandler> handelNotExisteException(NotExisteException notExisteException) {
        ErrorHandler errorHandler = new ErrorHandler(HttpStatus.NOT_FOUND.value(), notExisteException.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(errorHandler, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler
    public ResponseEntity<ErrorHandler> handleInvalidResourceExcception(InvalidResourceException notExisteException) {
        ErrorHandler errorHandler = new ErrorHandler(HttpStatus.BAD_REQUEST.value(), notExisteException.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(errorHandler, HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity<ErrorHandler> handelRuntimeException(RuntimeException exception){
        ErrorHandler errorHandler = new ErrorHandler(HttpStatus.NOT_ACCEPTABLE.value(),exception.getMessage(),System.currentTimeMillis());
        return new ResponseEntity<>(errorHandler,HttpStatus.NOT_ACCEPTABLE);
    }

}
