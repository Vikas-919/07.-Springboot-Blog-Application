package com.codewithvikas.blog.exception;

import org.springframework.http.HttpStatus;

public class BlogAPIException extends RuntimeException {

	private HttpStatus status;
	private String message;
	
	public BlogAPIException(HttpStatus status, String message) {
		this.status = status;
		this.message = message;
	}

	public BlogAPIException(String message, HttpStatus status, String message2) {
		super(message);
		this.status = status;
		this.message = message2;
	}

	public HttpStatus getStatus() {
		return status;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
}
