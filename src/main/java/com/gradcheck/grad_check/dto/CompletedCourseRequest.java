package com.gradcheck.grad_check.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompletedCourseRequest {
    private Long courseId;
    private int grade;
}
