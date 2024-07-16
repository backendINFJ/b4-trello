package com.nbcamp.b4trello.dto;

import org.springframework.http.HttpStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommonResponse<T> {
	private final HttpStatus statusCode;
	private final String message;
	private final T data;

	@Builder
	public CommonResponse(ResponseEnum responseEnum, T data) {
		this.statusCode = responseEnum.getHttpStatus();
		this.message = responseEnum.getMessage();
		this.data = data;
	}
}