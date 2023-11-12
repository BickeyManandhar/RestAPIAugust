package com.exception;

public class UserDoesNotExist extends RuntimeException{
	
	public UserDoesNotExist(String msg) {
		super(msg);
	}

}
