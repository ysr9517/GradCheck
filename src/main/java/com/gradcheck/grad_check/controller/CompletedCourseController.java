package com.gradcheck.grad_check.controller;

import com.gradcheck.grad_check.dto.CompletedCourseDto;
import com.gradcheck.grad_check.dto.CourseDto;
import com.gradcheck.grad_check.service.CompletedCourseService;
import com.gradcheck.grad_check.service.CourseService;
import com.gradcheck.grad_check.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/api/course")
public class CompletedCourseController {
    private final MemberService memberService;
    private final CompletedCourseService completedCourseService;
    private final CourseService courseService;
    // 이수과목 조회
    @GetMapping("/{memberName}/completed-courses")
    public String getCompletedCourses(@PathVariable String memberName, Model model) {
        Long memberId = memberService.getMemberByUsername(memberName).getId();
        List<CompletedCourseDto> completedCourses = completedCourseService.findAllCompletedCourses(memberId);
        model.addAttribute("completedCourses", completedCourses);
        model.addAttribute("memberId", memberId);
        return "completedCourseList";
    }

    // 이수과목 추가
    @PostMapping("/{memberName}/completed-courses")
    public String addCompletedCourse(@PathVariable String memberName,
                                     @RequestParam String courseName,
                                     @RequestParam int grade) {
        Long memberId = memberService.getMemberByUsername(memberName).getId();
        CourseDto course = courseService.findCourseByName(courseName);
        completedCourseService.createCompletedCourse(memberId, course.getId(), grade);
        return "redirect:/main";
    }

    // 이수과목 삭제
    @PostMapping("/{memberName}/completed-courses/{courseId}/delete")
    public String deleteCompletedCourse(@PathVariable String memberName, @PathVariable Long courseId) {
        Long memberId = memberService.getMemberByUsername(memberName).getId();
        completedCourseService.deleteCourse(memberId, courseId);
        return "redirect:/main";
    }

}
