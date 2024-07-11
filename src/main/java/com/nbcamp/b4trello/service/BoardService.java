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
import com.nbcamp.b4trello.repository.UserRepository;
import com.nbcamp.b4trello.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.lang.model.type.ErrorType;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserBoardRepository userBoardRepository;
    private final UserRepository userRepository;

    /**
     * 보드 생성 메서드
     *
     * @param userBoard 유저의 권한을 담고있는 객체
     * @param requestDto  보드 생성 요청 데이터를 담고 있는 객체
     * @return 권한이 있는경우 보드를 생성, 없는 경우 RuntimeException 발생
     */

    @Transactional
    public BoardResponseDto createBoard(BoardRequestDto requestDto, UserBoard userBoard) {
        if (userBoard.getUserType() == UserType.MANAGER) {
            Board board = Board.builder()
                    .boardName(requestDto.getBoardName())
                    .description(requestDto.getDescription())
                    .build();
            boardRepository.save(board);
            return new BoardResponseDto(board);
        } else {
            throw new RuntimeException(ErrorMessageEnum.BOARD_NOT_UNAUTHORIZED.getMessage());
        }
    }


    /**
     * 유저가 생성한 모든 보드 조회
     *
     * @param user Board를 조회할 User 객체
     * @return User의 Board정보를 BoardResponseDto로 변환한 List 반환
     */

    @Transactional(readOnly = true)
    public List<BoardResponseDto> getAllBoards(User user) {
        List<Board> boards = boardRepository.findAllByUser(user);
        return boards.stream().map(BoardResponseDto::new).toList();
    }

    /**
     * 보드 수정 메서드
     *
     * @param boardId 수정할 보드의 ID
     * @param requestDto 수정할 객체
     * @param userBoard 유저 정보 객체
     * @return 수정된 보드를 BoardResponse 반환
     * @throws RuntimeException 수정 권한이 없거나, 보드가 존재하지 않는 경우 예외 발생
     */

    @Transactional
    public BoardResponseDto updateBoard(Long boardId, BoardRequestDto requestDto, UserBoard userBoard) {
        Board board = findById(boardId);

        User user = userBoard.getUser();
        if (user == null) {
            throw new RuntimeException(ErrorMessageEnum.USER_NOT_FOUND.getMessage());
        }

        if (!userBoard.getUserType().equals(UserType.MANAGER)) {
            throw new RuntimeException("수정할 권한이 없습니다.");
        }

        board.update(requestDto);
        boardRepository.save(board);
        return new BoardResponseDto(board);
    }

    /**
     *
     * @param boardId, userBoard를 통해 삭제 권한, 보드 아이디 확인
     * @return 확인된 정보를 통해 보드 삭제
     */

    @Transactional
    public void deleteBoard(Long boardId, UserBoard userBoard) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException(ErrorMessageEnum.BOARD_NOT_FOUND.getMessage()));

        if (!userBoard.getUserType().equals(UserType.MANAGER)) {
            throw new RuntimeException("삭제할 권한이 없습니다.");
        }

        boardRepository.delete(board);
    }

    /**
     *
     * @param userBoard 유저 권한 체크 MANAGER or USer
     * @param id -> 해당 보드 아이디
     * @param userEmail -> 사용자 초대
     * @return 사용자 초대 완료
     */
    @Transactional
    public String inviteUser(UserBoard userBoard, Long id, String userEmail) {

        if (userBoard.getUserType() == UserType.USER) {
            throw new RuntimeException(ErrorMessageEnum.BOARD_NOT_INVITED.getMessage());
        }

        Board board = boardRepository.findById(id).orElseThrow();
        Optional<UserBoard> userBoardOptional = userBoardRepository.findByUserEmail(userEmail);

        // 이미 초대 된 사용자를 초대 한 경우
        if (userBoardOptional.isPresent()) {
            throw new RuntimeException(ErrorMessageEnum.BOARD_NOT_INVIATION.getMessage());
        }

        User invitedUser = userRepository.findByEmail(userEmail).orElseThrow(
                () -> new RuntimeException(ErrorMessageEnum.USER_NOT_FOUND.getMessage())
        );

        // 해당 유저쪽 개발되면 리팩토링 예정
        User changedUser = User.builder().email(invitedUser.getEmail())
                .password(invitedUser.getPassword())
                .name(invitedUser.getName())
                .refreshToken(invitedUser.getRefreshToken())
                .role(UserType.UserType_MANAGER) // 튜텨님 질문 통해 해결
                .build();

        UserBoard inviteUser = UserBoard.builder()
                .user(changedUser).board(board).build();

        userBoardRepository.save(userBoard);
        return userEmail + " 사용자를 초대 완료하였습니다.";

    }


    @Transactional(readOnly = true)
    public Board findById(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(() -> new RuntimeException(ErrorMessageEnum.BOARD_NOT_FOUND.getMessage()));
    }
}

