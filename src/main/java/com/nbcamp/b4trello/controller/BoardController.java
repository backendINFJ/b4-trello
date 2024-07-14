package com.nbcamp.b4trello.controller;

import com.nbcamp.b4trello.dto.*;
import com.nbcamp.b4trello.entity.Board;
import com.nbcamp.b4trello.entity.User;
import com.nbcamp.b4trello.entity.UserBoard;
import com.nbcamp.b4trello.repository.UserBoardRepository;
import com.nbcamp.b4trello.repository.UserRepository;
import com.nbcamp.b4trello.security.UserDetailsImpl;
import com.nbcamp.b4trello.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/boards")
@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    private final UserRepository userRepository;
    private final UserBoardRepository userBoardRepository;

    /**
     * 보드 생성
     *
     * @param userDetails     사용자의 정보
     * @param boardRequestDto 보드생성 요청 데이터
     * @return 201 OK,"보드 생성 완료" 보드 생성
     */
    @PostMapping
    public ResponseEntity<CommonResponse<BoardResponseDto>> createBoard(
            @AuthenticationPrincipal UserDetailsImpl userDetails, @Valid @RequestBody BoardRequestDto boardRequestDto) {

        BoardResponseDto boardResponseDto = boardService.createBoard(boardRequestDto, userDetails);
        CommonResponse<BoardResponseDto> response = CommonResponse.<BoardResponseDto>builder()
                .responseEnum(ResponseEnum.CREATE_BOARD)
                .data(boardResponseDto)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 보드 전체조회
     *
     * @param userDetails 로그인한 사용자의 정보
     * @return 200 OK, "보드 전체조회 성공", 보드 전체목록 반환
     */

    @GetMapping
    public ResponseEntity<CommonResponse<List<BoardResponseDto>>> getBoards(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        List<BoardResponseDto> boardList = boardService.getAllBoards();

        //생성자 변경
        CommonResponse<List<BoardResponseDto>> response = CommonResponse.<List<BoardResponseDto>>builder()
                .responseEnum(ResponseEnum.READ_BOARD)
                .data(boardList)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * 보드 수정 메서드
     *
     * @param userDetails 유저정보, 수정할 보드의 id, 수정할 보드의 정보
     * @return 200 OK, "보드 수정 성공", 수정된 보드 반환
     */

    @PatchMapping("/{board-id}")
    public ResponseEntity<CommonResponse<BoardResponseDto>> updateBoard(
            @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable("board-id") Long boardId,
            @Valid @RequestBody BoardRequestDto boardRequestDto) {

        UserBoard userBoard = userBoardRepository.findByUserAndBoard(userDetails.getUser(), boardService.findById(boardId))
                .orElseThrow(() -> new RuntimeException(ErrorMessageEnum.BOARD_NOT_FAILEINVIATED.getMessage()));

        BoardResponseDto boardResponseDto = boardService.updateBoard(boardId, boardRequestDto, userBoard);

        CommonResponse<BoardResponseDto> response = CommonResponse.<BoardResponseDto>builder()
                .responseEnum(ResponseEnum.UPDATE_BOARD)
                .data(boardResponseDto)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * @param boardId,userBoard를 통해 권한,보드 아이디 확인
     * @return 200 OK, "보드 삭제 성공", 보드의 모든 데이터 삭제
     */

    @DeleteMapping("/{board-id}")
    public ResponseEntity<CommonResponse<String>> deleteBoard(
            @PathVariable("board-id") Long boardId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        boardService.deleteBoard(boardId, userDetails);

        CommonResponse<String> response = CommonResponse.<String>builder()
                .responseEnum(ResponseEnum.DELETE_BOARD)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * @param userDetails,boardId,userEmail 으로 권한 확인과 동시에 초대할 보드 체크
     * @return 200 OK, "해당 보드에 사용자 초대 완료", 해당 보드에 사용자 초대 후 권한 생성
     */

    @PostMapping("/{board-id}/invitation")
    public ResponseEntity<CommonResponse<String>> inviteUser(
            @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable("board-id") Long boardId,
            @RequestBody BoardInviationRequestDto requestDto) {

        User user = userDetails.getUser();
        Board board = boardService.findById(boardId);

        UserBoard userBoard = userBoardRepository.findByUserAndBoard(user, board)
                .orElseThrow(() -> new RuntimeException(ErrorMessageEnum.BOARD_NOT_FAILEINVIATED.getMessage()));

        String message = boardService.inviteUser(userBoard, boardId, requestDto.getUserEmail());

        CommonResponse<String> response = CommonResponse.<String>builder()
                .responseEnum(ResponseEnum.INVITE_USER)
                .data(message)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}