package com.nbcamp.b4trello.controller;

import com.nbcamp.b4trello.dto.BoardRequestDto;
import com.nbcamp.b4trello.dto.BoardResponseDto;
import com.nbcamp.b4trello.dto.CommonResponse;
import com.nbcamp.b4trello.dto.ResponseEnum;
import com.nbcamp.b4trello.entity.UserBoard;
import com.nbcamp.b4trello.security.UserDetailsImpl;
import com.nbcamp.b4trello.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    /**
     * 보드 생성
     *
     * @param userBoard       사용자의 정보
     * @param boardRequestDto 보드생성 요청 데이터
     * @return 201 OK,"보드 생성 완료" 보드 생성
     */
    @PostMapping()
    public ResponseEntity<CommonResponse<BoardResponseDto>> createBoard(
            @AuthenticationPrincipal UserBoard userBoard, @Valid @RequestBody BoardRequestDto boardRequestDto) {

        BoardResponseDto boardResponseDto = boardService.createBoard(boardRequestDto, userBoard);
        CommonResponse<BoardResponseDto> response = CommonResponse.<BoardResponseDto>builder()
                .statusCode(HttpStatus.CREATED)
                .message(ResponseEnum.CREATE_BOARD.getMessage())
                .data(boardResponseDto)
                .build();

        return ResponseEntity.status(ResponseEnum.CREATE_BOARD.getHttpStatus()).body(response);
    }

    /**
     * 보드 전체조회
     *
     * @param userDetails 로그인한 사용자의 정보
     * @return 200 OK, "보드 전체조회 성공", 보드 전체목록 반환
     */

    @GetMapping
    public ResponseEntity<CommonResponse<List<BoardResponseDto>>> getBoards
    (@AuthenticationPrincipal UserDetailsImpl userDetails) {

        List<BoardResponseDto> boardList = boardService.getAllBoards(userDetails.getUser());
        CommonResponse<List<BoardResponseDto>> response = CommonResponse.<List<BoardResponseDto>>builder()
                .statusCode(HttpStatus.OK)  // 제거해도 될듯한 코드 중복이긴하나 가독성(?)을 위해 남겨둠
                .message(ResponseEnum.READ_BOARD.getMessage())
                .data(boardList)
                .build();

        return ResponseEntity.status(ResponseEnum.READ_BOARD.getHttpStatus()).body(response);
    }

    /**
     * 보드 수정 메서드
     *
     * @param userDetails 유저정보, 수정할 보드의 id, 수정할 보드의 정보
     * @return 200 OK, "보드 수정 성공", 수정된 보드 반환
     */

    @PatchMapping("/{boardId}")
    public ResponseEntity<CommonResponse<BoardResponseDto>> updateBoard(
            @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long boardId, @Valid @RequestBody BoardRequestDto boardRequestDto) {

        BoardResponseDto boardResponseDto = boardService.updateBoard(boardId, boardRequestDto, userDetails.getUser();
        CommonResponse<BoardResponseDto> response = CommonResponse.<BoardResponseDto>builder()
                .statusCode(HttpStatus.OK)
                .message(ResponseEnum.UPDATE_BOARD.getMessage())
                .data(boardResponseDto)
                .build();

        return ResponseEntity.status(ResponseEnum.UPDATE_BOARD.getHttpStatus()).body(response);
    }

    /**
     * @param boardId,userBoard를 통해 권한,보드 아이디 확인
     * @return200 OK, "보드 삭제 성공", 보드의 모든 데이터 삭제
     */

    @DeleteMapping("/{boardId}")
    public ResponseEntity<CommonResponse<String>> deleteBoard(
            @AuthenticationPrincipal UserBoard userBoard, @PathVariable Long boardId) {
        boardService.deleteBoard(boardId, userBoard);
        CommonResponse<String> response = CommonResponse.<String>builder()
                .statusCode(HttpStatus.OK)
                .message(ResponseEnum.DELETE_BOARD.getMessage())
                .data(null) // null값 허용x -> 그냥 .data 자체 삭제
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
