package com.gradcheck.grad_check.controller;

import com.gradcheck.grad_check.dto.CourseDto;
import com.gradcheck.grad_check.service.CourseService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Getter
@Setter
@Controller
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    //전체과목 조회
    @GetMapping
    public List<CourseDto> getAllCourses(){
        return courseService.findByCourse();
    }

    //특정과목 조회
    @GetMapping("/{id}")
    public ResponseEntity<CourseDto> getCourse(@PathVariable Long id) {
        CourseDto course = courseService.findCourseById(id);
        return ResponseEntity.ok(course);
    }

    //과목 추가
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CourseDto> createCourse(@RequestBody @Valid CourseDto courseDTO) {
        CourseDto savedCourse = courseService.createCourse(courseDTO);
        return ResponseEntity.ok(savedCourse);
    }

    // 과목 정보 수정
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CourseDto> updateCourse(@PathVariable Long id, @RequestBody @Valid CourseDto courseDTO) {
        CourseDto updatedCourse = courseService.updateCourse(id, courseDTO);
        return ResponseEntity.ok(updatedCourse);
    }

    // 과목 삭제
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
}
