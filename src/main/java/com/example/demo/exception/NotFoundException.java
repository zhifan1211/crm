package com.example.demo.exception;

import lombok.Data;

@Data
public class NotFoundException extends RuntimeException{
	private String code;

    public NotFoundException(String code, String message) {
        super(message);
        this.code = code;
    }
}
