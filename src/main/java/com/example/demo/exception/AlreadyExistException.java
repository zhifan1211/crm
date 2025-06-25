package com.example.demo.exception;

import lombok.Data;

@Data
public class AlreadyExistException extends RuntimeException {
    private final String code;

    public AlreadyExistException(String code, String message) {
        super(message);
        this.code = code;
    }
}
