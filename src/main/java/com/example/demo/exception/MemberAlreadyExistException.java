package com.example.demo.exception;

public class MemberAlreadyExistException extends MemberException{
	public MemberAlreadyExistException (String message) {
		super(message);
	}
}
