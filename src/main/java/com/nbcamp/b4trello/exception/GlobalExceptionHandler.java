package com.nbcamp.b4trello.exception;

import com.nbcamp.b4trello.dto.ErrorMessageDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ErrorMessageDto> illegalArgumentExceptionHandler(IllegalArgumentException ex) {
        ErrorMessageDto errorMessageDTO = new ErrorMessageDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(
                errorMessageDTO,HttpStatus.BAD_REQUEST);
    }
}
