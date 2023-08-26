package com.app.FirstApp.config.customException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotExisteException extends RuntimeException {
    public NotExisteException(String message){
        super(message);
    }
}
