package com.nbcamp.b4trello.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResponseMessage<T> {
    private Integer statusCode;
    private String message;
    private T data;
}