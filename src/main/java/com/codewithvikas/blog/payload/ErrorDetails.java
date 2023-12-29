package com.codewithvikas.blog.payload;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
		description = "ErroDetails Model Information"
)
public class ErrorDetails {
	
	@Schema(
			description = "Error Timestamp"
	)
	private Date timestamp;
	
	@Schema(
			description = "Error Message"
	)
	private String message;
	
	@Schema(
			description = "Error Detail"
	)
	private String detail;
	
	public ErrorDetails(Date timestamp, String message, String detail) {
		this.timestamp = timestamp;
		this.message = message;
		this.detail = detail;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public String getMessage() {
		return message;
	}

	public String getDetail() {
		return detail;
	}
	
}
