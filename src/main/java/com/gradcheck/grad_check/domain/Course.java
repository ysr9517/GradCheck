package com.gradcheck.grad_check.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    @Id
    @GeneratedValue
    @Column(name = "course_id")
    private Long id;

    private String name;

    private int credit;

    private String category;

    private String department;

    private boolean isRequired;

    @OneToMany(mappedBy = "course")
    @Builder.Default
    private List<CompletedCourse> completedCourses = new ArrayList<>();

    @ManyToMany(mappedBy = "courses")
    @Builder.Default
    private List<Curriculum> curriculums = new ArrayList<>();


}
