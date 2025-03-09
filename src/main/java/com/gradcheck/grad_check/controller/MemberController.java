package com.gradcheck.grad_check.controller;

import com.gradcheck.grad_check.domain.CompletedCourse;
import com.gradcheck.grad_check.dto.*;
import com.gradcheck.grad_check.domain.Member;
import com.gradcheck.grad_check.service.CompletedCourseService;
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
@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final CompletedCourseService completedCourseService;

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

    @PutMapping("/{id}")
    public ResponseEntity<MemberDTO> updateMember(@PathVariable Long id,@ModelAttribute MemberDTO memberDTO){
        return ResponseEntity.ok( memberService.updateMember(id, memberDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id){
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }

    //이수과목 찾기
    @GetMapping
    public List<CourseDto> getUserCompletedCourses(@PathVariable Long memberId) {
        return memberService.findAllCourses(memberId);
    }
    //이수과목 추가
    @PostMapping("/{courseId}")
    public ResponseEntity<CompletedCourse> addCompletedCourse(@PathVariable Long memberId, @PathVariable Long courseId,@PathVariable int grade) {
        CompletedCourse completedCourse= completedCourseService.createCompletedCourse(memberId, courseId,grade);
        return ResponseEntity.ok(completedCourse);
    }
    //이수과목 삭제
    @DeleteMapping("/{courseId}")
    public ResponseEntity<Void> deleteCompletCourse(@PathVariable Long userId, @PathVariable Long courseId){
        completedCourseService.deleteCourse(userId, courseId);
        return ResponseEntity.noContent().build();
    }
}
