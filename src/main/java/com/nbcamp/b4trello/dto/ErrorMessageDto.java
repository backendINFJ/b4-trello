package com.nbcamp.b4trello.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
@Getter
@Builder
@AllArgsConstructor
public class ErrorMessageDto {
        private String errorMessage;
        private int statusCode;

}
