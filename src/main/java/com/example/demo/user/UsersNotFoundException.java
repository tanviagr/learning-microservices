package com.example.demo.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class UsersNotFoundException extends RuntimeException {

	public UsersNotFoundException(String message) {
		super(message);
	}

}
