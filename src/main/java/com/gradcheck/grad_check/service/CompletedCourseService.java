package com.gradcheck.grad_check.service;


import com.gradcheck.grad_check.domain.CompletedCourse;
import com.gradcheck.grad_check.domain.Course;
import com.gradcheck.grad_check.domain.Member;
import com.gradcheck.grad_check.repository.CompletedCourseRepository;
import com.gradcheck.grad_check.repository.CourseRepository;
import com.gradcheck.grad_check.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompletedCourseService {
    private final CourseRepository courseRepository;
    private final MemberRepository memberRepository;
    private final CompletedCourseRepository completedCourseRepository;

    @Transactional
    public CompletedCourse createCompletedCourse(Long memberId, Long courseId,int grade) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()-> new RuntimeException("해당 id 찾을 수 없음"));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(()-> new RuntimeException("해당 id 찾을 수 없음"));

        CompletedCourse completedCourse = CompletedCourse.builder()
                .member(member)
                .course(course)
                .grade(grade)
                .build();
        return completedCourseRepository.save(completedCourse);
    }

    @Transactional
    public void deleteCourse(Long memberId, Long courseId) {
        CompletedCourse completedCourse = completedCourseRepository.findByMemberIdAndCourseId(memberId, courseId)
                .orElseThrow(() -> new RuntimeException("해당 이수 과목을 찾을 수 없음"));

        completedCourseRepository.delete(completedCourse);
    }

}
