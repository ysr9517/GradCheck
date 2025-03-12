package com.gradcheck.grad_check.controller;

import com.gradcheck.grad_check.dto.*;
import com.gradcheck.grad_check.service.CompletedCourseService;
import com.gradcheck.grad_check.service.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Getter
@Setter
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

    // 이수과목 조회
    @GetMapping("/{memberId}/completed-courses")
    public String getCompletedCourses(@PathVariable Long memberId, Model model) {
        List<CompletedCourseDto> completedCourses = completedCourseService.findAllCompletedCourses(memberId);
        model.addAttribute("completedCourses", completedCourses);
        model.addAttribute("memberId", memberId);
        return "completedCourseList";
    }

    // 이수과목 추가
    @PostMapping("/{memberId}/completed-courses")
    public String addCompletedCourse(@PathVariable Long memberId,
                                     @RequestParam Long courseId,
                                     @RequestParam int grade) {
        completedCourseService.createCompletedCourse(memberId, courseId, grade);
        return "redirect:/api/members/" + memberId + "/completed-courses";
    }

    // 이수과목 삭제
    @PostMapping("/{memberId}/completed-courses/{courseId}/delete")
    public String deleteCompletedCourse(@PathVariable Long memberId, @PathVariable Long courseId) {
        completedCourseService.deleteCourse(memberId, courseId);
        return "redirect:/api/members/" + memberId + "/completed-courses";
    }

}

