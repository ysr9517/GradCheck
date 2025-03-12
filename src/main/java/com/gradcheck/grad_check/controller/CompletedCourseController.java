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
                                     @RequestParam String courseName,
                                     @RequestParam int grade) {
        CourseDto course = courseService.findCourseByName(courseName);
        completedCourseService.createCompletedCourse(memberId, course.getId(), grade);
        return "redirect:/api/course/" + memberId + "/completed-courses";
    }

    // 이수과목 삭제
    @PostMapping("/{memberId}/completed-courses/{courseId}/delete")
    public String deleteCompletedCourse(@PathVariable Long memberId, @PathVariable Long courseId) {
        completedCourseService.deleteCourse(memberId, courseId);
        return "redirect:/api/course/" + memberId + "/completed-courses";
    }

}
