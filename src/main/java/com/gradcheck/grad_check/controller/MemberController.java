package com.gradcheck.grad_check.controller;

import com.gradcheck.grad_check.domain.CompletedCourse;
import com.gradcheck.grad_check.dto.*;
import com.gradcheck.grad_check.domain.Member;
import com.gradcheck.grad_check.service.CompletedCourseService;
import com.gradcheck.grad_check.service.CourseService;
import com.gradcheck.grad_check.service.MemberService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Getter
@Setter
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;
    private final CompletedCourseService completedCourseService;
    private final CourseService courseService;
    @PostMapping("/sign-up")
    public ResponseEntity<MemberDTO> signUp(@RequestBody SignUpDTO signUpDto) {
        MemberDTO savedMemberDto = memberService.signUp(signUpDto);
        return ResponseEntity.ok(savedMemberDto);
    }

    @PostMapping("/sign-in")
    public JwtToken signIn(@RequestBody SignInDTO signInDTO) {
        String username = signInDTO.getUsername();
        String password = signInDTO.getPassword();
        JwtToken jwtToken = memberService.signIn(username, password);
        log.info("request username = {}, password = {}", username, password);
        log.info("jwtToken accessToken = {}, refreshToken = {}", jwtToken.getAccessToken(), jwtToken.getRefreshToken());
        return jwtToken;
    }


    @GetMapping("/me/{username}")
    public ResponseEntity<MemberDTO> getMebmberDetail(@PathVariable String username) {
        return ResponseEntity.ok(memberService.getMemberByUsername(username));
    }


}

