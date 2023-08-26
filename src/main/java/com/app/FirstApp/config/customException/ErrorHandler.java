package com.app.FirstApp.config.customException;

import lombok.Data;

@Data
public class ErrorHandler {
    private int statusCode;
    private String message;
    private Long timeStamp;

    public ErrorHandler(int statusCode, String message, Long timeStamp) {
        this.statusCode = statusCode;
        this.message = message;
        this.timeStamp = timeStamp;
    }
}
