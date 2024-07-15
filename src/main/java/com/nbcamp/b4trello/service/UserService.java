//package com.nbcamp.b4trello.service;
//
//import com.nbcamp.b4trello.dto.ErrorMessageEnum;
//import com.nbcamp.b4trello.dto.UserRequestDto;
//import com.nbcamp.b4trello.dto.UserResponseDto;
//import com.nbcamp.b4trello.dto.UserUpdateRequestDto;
//import com.nbcamp.b4trello.dto.UserUpdateResponseDto;
//import com.nbcamp.b4trello.entity.User;
//import com.nbcamp.b4trello.enums.StatusEnum;
//import com.nbcamp.b4trello.repository.UserRepository;
//import java.util.Objects;
//import java.util.Optional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@RequiredArgsConstructor
//@Service
//public class UserService {
//    private final BCryptPasswordEncoder bCryptPasswordEncoder;
//    private final UserRepository userRepository;
//    /**
//     * 유저 생성 메서드
//     * @param userDto
//     * @return UserResponseDto
//     */
//    @Transactional
//    public UserResponseDto createUser(UserRequestDto userDto) {
//
//        Optional<User> checkUsername = userRepository.findByUsername(userDto.getUsername());
//        if (checkUsername.isPresent()) {
//            throw new IllegalArgumentException(ErrorMessageEnum.ALREADY_USER.getMessage());
//        }
//        User user = new User(
//                userDto.getUsername(), bCryptPasswordEncoder.encode(userDto.getPassword()),
//                userDto.getNickname(), userDto.getEmail());
//
//        userRepository.save(user);
//        return UserResponseDto.builder().username(user.getUsername()).build();
//    }
//
//    /**
//     * 유저 업데이트 메서드
//     * @param userId
//     * @param updateDTO
//     * @param user
//     * @return UserUpdateResponseDto
//     */
//    @Transactional
//    public UserUpdateResponseDto updateUser(Long userId, UserUpdateRequestDto updateDTO, User user) {
//
//            if(!user.getId().equals(userId)){
//                throw new IllegalArgumentException(ErrorMessageEnum.PRIVATE_USER.getMessage());
//            }
//            if(bCryptPasswordEncoder.matches(updateDTO.getPassword(),user.getPassword())){
//                throw new IllegalArgumentException(ErrorMessageEnum.SAME_PASSWORD.getMessage());
//            }
//
//            UserUpdateRequestDto updateUser = null;
//             updateUser.builder()
//                    .nickname(updateDTO.getNickname())
//                    .password(updateDTO.getPassword())
//                    .build();
//
//
//        Optional<User> originUser = userRepository.findById(userId);
//        if (originUser.isEmpty()) {
//            throw new IllegalArgumentException(ErrorMessageEnum.USER_NOT_FOUND.getMessage());
//        }
//        originUser.get().updateUser(updateUser);
//        return UserUpdateResponseDto.builder().nickname(originUser.get().getNickname()).build();
//    }
//
//    /**
//     * 유저 탈퇴 메서드
//     * @param userId
//     * @param user
//     */
//    public void deleteUser(Long userId, User user) {
//
//        Optional<User> originUser = userRepository.findById(userId);
//        if (originUser.isEmpty() || originUser.get().getStatus().equals(StatusEnum.DENIED)) {
//            throw new IllegalArgumentException(ErrorMessageEnum.USER_NOT_FOUND.getMessage());
//        }
//
//        if(!validateUser(user.getId(), originUser.get().getId())){
//            throw new IllegalArgumentException(ErrorMessageEnum.PRIVATE_USER.getMessage());
//        }
//
//
//        originUser.get().deleteUser();
//        userRepository.save(originUser.get());
//    }
//
//    // 아이디 일치하는지 검증
//    private boolean validateUser(Long userId, Long originId) {
//        if (!Objects.equals(userId, originId)) {
//            return false;
//        }
//        return true;
//    }
//
//}



package com.nbcamp.b4trello.service;

import com.nbcamp.b4trello.dto.ErrorMessageEnum;
import com.nbcamp.b4trello.dto.UserRequestDto;
import com.nbcamp.b4trello.dto.UserResponseDto;
import com.nbcamp.b4trello.dto.UserUpdateRequestDto;
import com.nbcamp.b4trello.dto.UserUpdateResponseDto;
import com.nbcamp.b4trello.entity.User;
import com.nbcamp.b4trello.enums.StatusEnum;
import com.nbcamp.b4trello.repository.UserRepository;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
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
   * @return UserResponseDto
   */
  @Transactional
  public UserResponseDto createUser(UserRequestDto userDto) {
    Optional<User> checkUsername = userRepository.findByUsername(userDto.getUsername());
    if (checkUsername.isPresent()) {
      throw new IllegalArgumentException(ErrorMessageEnum.ALREADY_USER.getMessage());
    }
    User user = new User(
        userDto.getUsername(), bCryptPasswordEncoder.encode(userDto.getPassword()),
        userDto.getNickname(), userDto.getEmail());
    userRepository.save(user);
    return UserResponseDto.builder().username(user.getUsername()).build();
  }

  /**
   * 유저 업데이트 메서드
   * @param userId
   * @param updateDTO
   * @param user
   * @return UserUpdateResponseDto
   */
  @Transactional
  public UserUpdateResponseDto updateUser(Long userId, UserUpdateRequestDto updateDTO, User user) {
    if (!user.getId().equals(userId)) {
      throw new IllegalArgumentException(ErrorMessageEnum.PRIVATE_USER.getMessage());
    }
    if (bCryptPasswordEncoder.matches(updateDTO.getPassword(), user.getPassword())) {
      throw new IllegalArgumentException(ErrorMessageEnum.SAME_PASSWORD.getMessage());
    }

    Optional<User> originUser = userRepository.findById(userId);
    if (originUser.isEmpty()) {
      throw new IllegalArgumentException(ErrorMessageEnum.USER_NOT_FOUND.getMessage());
    }

    originUser.get().updateUser(UserUpdateRequestDto.builder()
        .nickname(updateDTO.getNickname())
        .password(bCryptPasswordEncoder.encode(updateDTO.getPassword()))
        .build());

    return UserUpdateResponseDto.builder().nickname(originUser.get().getNickname()).build();
  }

  /**
   * 유저 탈퇴 메서드
   * @param userId
   * @param user
   */
  @Transactional
  public void deleteUser(Long userId, User user) {
    Optional<User> originUser = userRepository.findById(userId);
    if (originUser.isEmpty() || originUser.get().getStatus().equals(StatusEnum.DENIED)) {
      throw new IllegalArgumentException(ErrorMessageEnum.USER_NOT_FOUND.getMessage());
    }
    if (!validateUser(user.getId(), originUser.get().getId())) {
      throw new IllegalArgumentException(ErrorMessageEnum.PRIVATE_USER.getMessage());
    }
    originUser.get().deleteUser();
    userRepository.save(originUser.get());
  }

  // 아이디 일치하는지 검증
  private boolean validateUser(Long userId, Long originId) {
    return Objects.equals(userId, originId);
  }
}
