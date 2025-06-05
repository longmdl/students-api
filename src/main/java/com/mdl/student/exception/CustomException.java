package com.mdl.student.exception;

public class CustomException extends Exception {

    String message;
    Integer code;

    public CustomException(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

    public CustomException(String message) {
        this.message = message;
        this.code = 500;
    }

    CustomException() {
        super();
    }
}
