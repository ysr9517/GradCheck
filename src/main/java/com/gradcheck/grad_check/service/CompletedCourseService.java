package com.gradcheck.grad_check.service;


import com.gradcheck.grad_check.domain.CompletedCourse;
import com.gradcheck.grad_check.domain.Course;
import com.gradcheck.grad_check.domain.Member;
import com.gradcheck.grad_check.dto.CompletedCourseDto;
import com.gradcheck.grad_check.dto.CourseDto;
import com.gradcheck.grad_check.repository.CompletedCourseRepository;
import com.gradcheck.grad_check.repository.CourseRepository;
import com.gradcheck.grad_check.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompletedCourseService {
    private final CourseRepository courseRepository;
    private final MemberRepository memberRepository;
    private final CompletedCourseRepository completedCourseRepository;

    @Transactional
    public CompletedCourseDto createCompletedCourse(Long memberId, Long courseId, int grade) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()-> new RuntimeException("해당 id 찾을 수 없음"));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(()-> new RuntimeException("해당 id 찾을 수 없음"));
        CompletedCourse completedCourse = CompletedCourse.builder()
                .member(member)
                .course(course)
                .grade(grade)
                .build();
        CompletedCourse saveCourse = completedCourseRepository.save(completedCourse);
        return CompletedCourseDto.form(saveCourse);
    }

    @Transactional
    public void deleteCourse(Long memberId, Long courseId) {
        CompletedCourse completedCourse = completedCourseRepository.findByMemberIdAndCourseId(memberId, courseId)
                .orElseThrow(() -> new RuntimeException("해당 이수 과목을 찾을 수 없음"));
        completedCourseRepository.delete(completedCourse);
    }

    public List<CompletedCourseDto> findAllCompletedCourses(Long userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(()->new IllegalStateException("회원이 존재하지 않습니다."));
        List<CompletedCourse> completedCourses = member.getCompletedCourses();
        List<CompletedCourseDto> completedCourseDtos = new ArrayList<>();
        for(CompletedCourse completedCourse:completedCourses){
            completedCourseDtos.add(CompletedCourseDto.form(completedCourse));
        }
        return completedCourseDtos;
    }
}
