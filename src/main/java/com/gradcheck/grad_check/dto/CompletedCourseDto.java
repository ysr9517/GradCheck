package com.gradcheck.grad_check.dto;

import com.gradcheck.grad_check.domain.CompletedCourse;
import com.gradcheck.grad_check.domain.Course;
import com.gradcheck.grad_check.domain.Member;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompletedCourseDto {
    private Long id;
    private Long memberId;
    private Course course;
    private int grade;

    public static CompletedCourseDto form(CompletedCourse completedCourse) {
        return CompletedCourseDto.builder()
                .id(completedCourse.getId())
                .memberId(completedCourse.getMember().getId())
                .course(completedCourse.getCourse())
                .grade(completedCourse.getGrade())
                .build();
    }

}
