package com.nbcamp.b4trello.dto;

import org.springframework.http.HttpStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommonResponse<T> {
	private HttpStatus statusCode;
	private String message;
	private T data;

	public CommonResponse(ResponseEnum responseEnum, T data) {
		this.statusCode = responseEnum.getHttpStatus();
		this.message = responseEnum.getMessage();
		this.data = data;
	}
}