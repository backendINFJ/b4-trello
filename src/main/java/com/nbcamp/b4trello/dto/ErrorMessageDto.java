package com.nbcamp.b4trello.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
public class ErrorMessageDto {
        private String errorMessage;
        private int statusCode;

}
