package com.gradcheck.grad_check.service;

import com.gradcheck.grad_check.domain.Course;
import com.gradcheck.grad_check.domain.Curriculum;
import com.gradcheck.grad_check.dto.CourseDto;
import com.gradcheck.grad_check.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;


    //전체 과목 조회
    public List<CourseDto> findByCourse() {
        List<Course> courses = courseRepository.findAll();
        List<CourseDto> courseDtos = new ArrayList<>();
        for (Course course : courses) {
            courseDtos.add(CourseDto.from(course));
        }
        return courseDtos;
    }

    //과목 만들기
    @Transactional
    public CourseDto createCourse(CourseDto courseDto) {
        Course course = Course.builder()
                .name(courseDto.getName())
                .credit(courseDto.getCredit())
                .category(courseDto.getCategory())
                .department(courseDto.getDepartment())
                .isRequired(courseDto.isRequired())
                .build();
        return CourseDto.from(courseRepository.save(course));
    }
    //과목 이름으로 찾기
    public CourseDto findCourseByName(String name) {

        return CourseDto.from(courseRepository.findByName(name).orElseThrow(()
                -> new RuntimeException("해당 과목 찾을 수 없음")));
    }
    //과목 id로 찾기
    public CourseDto findCourseById(Long id) {
        return CourseDto.from(courseRepository.findById(id).orElseThrow(()
                -> new RuntimeException("해당 과목 ID 찾을 수 없음")));
    }

    //과목 업데이트
    @Transactional
    public CourseDto updateCourse(Long id, CourseDto courseDto) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 과목을 찾을 수 없음"));

        Course updatedCourse = Course.builder()
                .id(course.getId())
                .name(courseDto.getName())
                .credit(courseDto.getCredit())
                .category(courseDto.getCategory())
                .department(courseDto.getDepartment())
                .isRequired(courseDto.isRequired())
                .build();

        return CourseDto.from(courseRepository.save(updatedCourse));
    }
    @Transactional
    public void deleteCourse(Long id) {
        if(!courseRepository.existsById(id)) {
            throw new RuntimeException("사용자를 찾을 수 없습니다.");
        }
        courseRepository.deleteById(id);
    }
}
