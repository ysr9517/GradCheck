package com.gradcheck.grad_check.dto;

import com.gradcheck.grad_check.domain.Course;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseDto {
    private Long id;

    private String name;

    private int credit;

    private String category;

    private String department;

    private boolean isRequired;

    static public CourseDto from(Course course) {
        return CourseDto.builder()
                .id(course.getId())
                .name(course.getName())
                .credit(course.getCredit())
                .category(course.getCategory())
                .department(course.getDepartment())
                .isRequired(course.isRequired())
                .build();

    }
}
