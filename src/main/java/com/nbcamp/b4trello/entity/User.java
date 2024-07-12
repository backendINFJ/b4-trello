package com.nbcamp.b4trello.entity;

import com.nbcamp.b4trello.dto.UserUpdateRequestDTO;
import com.nbcamp.b4trello.enums.StatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Entity(name = "users")
@NoArgsConstructor
@Table(name = "users")
public class User extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String email;

    @Setter
    @Enumerated(EnumType.STRING)
    private StatusEnum status = StatusEnum.ACTIVE;


    @Builder
    public User(String username, String password, String nickname, String email) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
    }
    // 프로필 저장
    public void updateUser(UserUpdateRequestDTO updateDTO) {
        this.nickname = updateDTO.getNickname();
        this.password = updateDTO.getPassword();
    }

    public void deleteUser() {
        this.status = StatusEnum.DENIED;
        this.setDeletedAt();
    }
    public void verifiStatus(){
        this.status = StatusEnum.VERYFICATION;
    }
}
