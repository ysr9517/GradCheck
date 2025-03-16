package com.gradcheck.grad_check.service;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomMemberDetail implements UserDetails {
    private Long id; // 사용자 고유 ID
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    // 생성자
    public CustomMemberDetail(Long id, String password, String username,
                              Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.password = password;
        this.username = username;
        this.authorities = authorities;
    }

    // 추가 메서드
    public Long getId() {return id;}

    // UserDetails 인터페이스 구현
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정이 만료되지 않았는지 여부
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 계정이 잠기지 않았는지 여부
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 자격 증명이 만료되지 않았는지 여부
    }

    @Override
    public boolean isEnabled() {
        return true; // 계정 활성화 여부
    }

}
