package com.nbcamp.b4trello.controller;

import com.nbcamp.b4trello.dto.BoardRequestDto;
import com.nbcamp.b4trello.dto.BoardResponseDto;
import com.nbcamp.b4trello.dto.CommonResponse;
import com.nbcamp.b4trello.dto.ResponseEnum;
import com.nbcamp.b4trello.security.UserDetailsImpl;
import com.nbcamp.b4trello.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    /**
     * 보드 생성
     *
     * @param userDetails 로그인한 사용자의 정보
     * @param boardRequestDto 보드생성 요청 데이터
     * @return ResponseEntity<CommonResponse<BoardResponseDto>> 반환
     */
    @PostMapping()
    public ResponseEntity<CommonResponse<BoardResponseDto>> createBoard(
            @AuthenticationPrincipal UserDetailsImpl userDetails, @Valid @RequestBody BoardRequestDto boardRequestDto) {

        BoardResponseDto boardresponseDto = boardService.createBoard(userDetails, boardRequestDto);
        CommonResponse<BoardResponseDto> response = CommonResponse.<BoardResponseDto>builder()
                .statusCode(ResponseEnum.CREATE_BOARD.getHttpStatus())
                .message(ResponseEnum.CREATE_BOARD.getMessage())
                .data(boardresponseDto)
                .build();

        return ResponseEntity.status(ResponseEnum.CREATE_BOARD.getHttpStatus()).body(response);
    }
}
