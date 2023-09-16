package com.app.FirstApp.config.customException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidResourceException extends RuntimeException {
    public InvalidResourceException(String msg){
        super(msg);
    }
}
