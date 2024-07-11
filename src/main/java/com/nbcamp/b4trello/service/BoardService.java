package com.nbcamp.b4trello.service;

import com.nbcamp.b4trello.dto.BoardRequestDto;
import com.nbcamp.b4trello.dto.BoardResponseDto;
import com.nbcamp.b4trello.dto.ErrorMessageEnum;
import com.nbcamp.b4trello.entity.Board;
import com.nbcamp.b4trello.entity.User;
import com.nbcamp.b4trello.entity.UserBoard;
import com.nbcamp.b4trello.entity.UserType;
import com.nbcamp.b4trello.repository.BoardRepository;
import com.nbcamp.b4trello.repository.UserBoardRepository;
import com.nbcamp.b4trello.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserBoardRepository userBoardRepository;

    /**
     * 보드 생성 메서드입니다.
     *
     * @param userDetails 로그인한 사용자의 정보를 담고 있는 {@link UserDetails} 객체
     * @param requestDto  보드 생성 요청 데이터를 담고 있는 {@link BoardRequestDto} 객체
     * 이 객체는 보드의 필수 데이터 이름(boardName)과 설명(description)을 포함
     *
     * @return 생성된 보드의 정보를 담고 있는 {@link BoardResponseDto} 객체를 반환
     * 이 객체는 생성된 보드의 ID(boardId), 이름(boardName), 설명(description) 등의 정보를 포함
     */

    public BoardResponseDto createBoard(UserDetailsImpl userDetails, BoardRequestDto requestDto) {
        User user = userDetails.getUser();

        if (user.getUserType() != UserType.MANAGER) { // 권한 체크  -> 리랙토링 요소
            // Manager만 보드 생성 가능 (?) -> 리팩토링 요소
            throw new RuntimeException(ErrorMessageEnum.BOARD_NOT_UNAUTHORIZED.getMessage());
        }

        Board board = Board.builder()
                .boardName(requestDto.getBoardName())
                .description(requestDto.getDescription())
                .build();

        Board savedBoard = boardRepository.save(board);

        UserBoard userBoard = UserBoard.builder()
                .user(user)
                .board(savedBoard)
                .userType(UserType.MANAGER)
                .build();

        userBoardRepository.save(userBoard);

        return new BoardResponseDto(savedBoard);
    }
}
