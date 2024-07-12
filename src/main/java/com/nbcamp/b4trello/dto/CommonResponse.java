package com.nbcamp.b4trello.dto;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Builder
public class CommonResponse<T> {
	private ResponseEnum status;
	private T data;

	public CommonResponse(ResponseEnum responseEnum, T data) {
		this.status = responseEnum;
		this.data = data;
	}
}