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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserBoardRepository userBoardRepository;

    /**
     * 보드 생성 메서드
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

    /**
     * 유저가 생성한 모든 보드 조회
     *
     * @param  user Board를 조회할 User 객체
     * @return User의 Board정보를 BoardResponseDto로 변환한 List 반환
     *
     */

    @Transactional(readOnly = true)
    public List<BoardResponseDto> getAllBoards(User user) {
        List<Board> boards = boardRepository.findAllByUser(user);
        return boards.stream().map(BoardResponseDto::new).toList();
    }
}
