package com.nbcamp.b4trello.entity;

import com.nbcamp.b4trello.dto.UserRequestDTO;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Entity(name = "users")
@NoArgsConstructor
@Table(name = "users")
public class User {
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
    private String status = "ACTIVE";


    @Builder
    public User(String username, String password, String nickname, String email) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
    }
    // 프로필 저장
    public void updateUser(UserRequestDTO userRequestDTO) {
        this.username = userRequestDTO.getUsername();
        this.password = userRequestDTO.getPassword();
        this.nickname = userRequestDTO.getNickname();
        this.email = userRequestDTO.getEmail();
    }
}
