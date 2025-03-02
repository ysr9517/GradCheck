package com.gradcheck.grad_check.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
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
    private List<CompletedCourse> completedCourses = new ArrayList<>();

    @ManyToMany(mappedBy = "courses")
    private List<Curriculum> curriculums = new ArrayList<>();


}
