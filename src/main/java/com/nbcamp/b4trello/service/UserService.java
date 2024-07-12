package com.nbcamp.b4trello.service;

import com.nbcamp.b4trello.dto.UserRequestDTO;
import com.nbcamp.b4trello.dto.UserUpdateDTO;
import com.nbcamp.b4trello.entity.User;
import com.nbcamp.b4trello.enums.StatusEnum;
import com.nbcamp.b4trello.repository.UserRepository;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    /**
     * 유저 생성 메서드
     * @param userDto
     * @return 완료 메시지
     */
    @Transactional
    public ResponseEntity<String> createUser(UserRequestDTO userDto) {

        Optional<User> checkUsername = userRepository.findByUsername(userDto.getUsername());
        if (checkUsername.isPresent()) {
            throw new RuntimeException("중복된 사용자");
        }
        User user = new User(
                userDto.getUsername(), bCryptPasswordEncoder.encode(userDto.getPassword()),
                userDto.getNickname(), userDto.getEmail());

        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body("가입 완료");
    }

    /**
     * 유저 업데이트 메서드
     * @param userId
     * @param updateDTO
     * @param user
     * @return 완료 메시지
     */
    @Transactional
    public ResponseEntity<String> updateUser(Long userId, UserUpdateDTO updateDTO, User user) {

            if(!user.getId().equals(userId)){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("다른 유저를 수정할 수 없습니다.");
            }
            if(bCryptPasswordEncoder.matches(updateDTO.getPassword(),user.getPassword())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("현재와 같은 비밀번호는 변경할수 없습니다.");
            }

            UserUpdateDTO updateUser = null;
             updateUser.builder()
                    .nickname(updateDTO.getNickname())
                    .password(updateDTO.getPassword())
                    .build();


        Optional<User> originUser = userRepository.findById(userId);
        if (originUser.isEmpty()) {
            throw new RuntimeException("유저를 찾을 수 없습니다.");
        }
        originUser.get().updateUser(updateUser);
        return ResponseEntity.status(HttpStatus.OK).body("수정완료");
    }

    /**
     * 유저 탈퇴 메서드
     * @param userId
     * @param user
     * @return 완료 메시지
     */
    public ResponseEntity<String> deleteUser(Long userId, User user) {

        Optional<User> originUser = userRepository.findById(userId);
        if (originUser.isEmpty() || originUser.get().getStatus().equals(StatusEnum.DENIED)) {
            throw new RuntimeException("유저를 찾을 수 없습니다.");
        }

        if(!validateUser(user.getId(), originUser.get().getId())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("다른 유저를 탈퇴할 수 없습니다.");
        }


        originUser.get().deleteUser();
        userRepository.save(originUser.get());
        return ResponseEntity.status(HttpStatus.OK).body("탈퇴 완료");
    }

    // 아이디 일치하는지 검증
    private boolean validateUser(Long userId, Long originId) {
        if (!Objects.equals(userId, originId)) {
            return false;
        }
        return true;
    }

}
