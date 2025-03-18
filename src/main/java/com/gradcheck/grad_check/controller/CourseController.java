package com.gradcheck.grad_check.controller;

import com.gradcheck.grad_check.dto.CourseDto;
import com.gradcheck.grad_check.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/courses")
public class CourseController {
    private final CourseService courseService;

    //전체과목 조회
    @GetMapping
    public String getAllCourses(Model model) {
        List<CourseDto> courseDtos = courseService.findByCourse();
        model.addAttribute("courses", courseDtos);
        return "/api/courseList";
    }

    //특정과목 조회
    @GetMapping("/{id}")
    public String getCourse(@PathVariable Long id, Model model) {
        CourseDto course = courseService.findCourseById(id);
        model.addAttribute("course", course);
        return "/api/courseDetail";
    }

    //과목 추가
    // @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public String createCourse(@RequestParam String name,
                             @RequestParam int credit,
                             @RequestParam String category,
                             @RequestParam String department,
                             @RequestParam(required = false, defaultValue = "false") boolean required) {
        CourseDto courseDto = CourseDto.builder()
                .name(name)
                .credit(credit)
                .category(category)
                .department(department)
                .isRequired(required)
                .build();
        courseService.createCourse(courseDto);
        return "redirect:/main";
    }
    // 과목 수정 페이지 이동
    @GetMapping("/{id}/edit")
    public String editCourse(@PathVariable Long id, Model model) {
        CourseDto courseDto = courseService.findCourseById(id);
        model.addAttribute("course", courseDto);
        return "editCourse"; // editCourse.html로 이동
    }

    // 과목 정보 수정
//    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}")
    public String updateCourse(@PathVariable Long id, @RequestParam String name,
                               @RequestParam int credit,
                               @RequestParam String category,
                               @RequestParam String department,
                               @RequestParam(required = false, defaultValue = "false") boolean required) {
        CourseDto courseDto = CourseDto.builder()
                .name(name)
                .credit(credit)
                .category(category)
                .department(department)
                .build();
        courseService.updateCourse(id, courseDto);
        return "redirect:/main";
    }

    // 과목 삭제
//    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}/delete")
    public String deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return "redirect:/main";
    }
}
