package com.nbcamp.b4trello.security;

import com.nbcamp.b4trello.entity.User;
import java.util.Collection;
import java.util.Collections;

import com.nbcamp.b4trello.enums.StatusEnum;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
public class UserDetailsImpl implements UserDetails {

    private final User user;

    public UserDetailsImpl(User user) {
        this.user = user;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (user.getStatus() == StatusEnum.VERYFICATION) {
            return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        } else if (user.getStatus() == StatusEnum.ACTIVE) {
            return Collections.singletonList(new SimpleGrantedAuthority("ROLE_MANAGER"));
        }
        return Collections.emptyList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.getStatus() != StatusEnum.DENIED;
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getStatus() != StatusEnum.DENIED;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.getStatus() != StatusEnum.DENIED;
    }

    @Override
    public boolean isEnabled() {
        return user.getStatus() != StatusEnum.DENIED;
    }
}

