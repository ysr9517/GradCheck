package com.gradcheck.grad_check.service;

import com.gradcheck.grad_check.domain.Member;
import com.gradcheck.grad_check.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("User not found"));
        return new CustomMemberDetail(
                member.getId(),
                member.getPassword(),
                member.getUsername(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")) // 권한 설정
        );
    }
    public UserDetails loadUserById(long id) throws UsernameNotFoundException {
        Member member = memberRepository.findById(id).orElseThrow(()->new UsernameNotFoundException("User not found"));
        return new CustomMemberDetail(
                member.getId(),
                member.getPassword(),
                member.getUsername(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")) // 권한 설정
        );
    }
}
