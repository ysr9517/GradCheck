package com.example.graduation.service;

import com.example.graduation.dto.JwtResponse;
import com.example.graduation.dto.LoginRequest;
import com.example.graduation.dto.SignupRequest;
import com.example.graduation.model.User;
import com.example.graduation.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    // 회원가입
    public String registerUser(SignupRequest request) {
        if (userRepository.existByUsername(request.getUsername())) {
            return "이미 존재하는 사용자입니다.";
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setUniversity(request.getUniversity());
        user.setDepartment(request.getDepartment());

        userRepository.save(user);
        return "회원가입 성공!";
    }

    // 로그인
    public JwtResponse loginUser(LoginRequest request) {
        Optional<User> user = userRepository.findByUsername(request.getUsername());

        if (user.isPresent() && passwordEncoder.matches(request.getPassword(),user.get().getPassword())) {
            String token = jwtTokenProvider.generateToken(user.get().getUsername());
            return new JwtResponse(token, user.get().getUsername());
        } else {
            throw new RuntimeException("로그인 실패 : 아이디 또는 비밀번호가 잘못되었습니다.");
        }
    }
}
