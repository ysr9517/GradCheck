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

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/member")
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
    @GetMapping("/{memberId}")
    public List<CompletedCourseDto> getUserCompletedCourses(@PathVariable Long memberId) {
        return completedCourseService.findAllCompletedCourses(memberId);
    }
    //이수과목 추가
    @PostMapping("/{memberId}/completed-courses")
    public ResponseEntity<CompletedCourseDto> addCompletedCourse(@PathVariable Long memberId,@RequestBody CompletedCourseRequest request) {
        CompletedCourseDto completedCourse= completedCourseService.createCompletedCourse(memberId, request.getCourseId(), request.getGrade());
        return ResponseEntity.ok(completedCourse);
    }
    //이수과목 삭제
    @DeleteMapping("/{memberId}/{courseId}")
    public ResponseEntity<Void> deleteCompletCourse(@PathVariable Long memberId, @PathVariable Long courseId) {
        completedCourseService.deleteCourse(memberId, courseId);
        return ResponseEntity.noContent().build();
    }
}
