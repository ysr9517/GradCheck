package com.gradcheck.grad_check.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Curriculum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "curriculum_id")
    private Long id;

    private String department;

    private LocalDate admissionYear;

    private int requiredMajorCredits;

    private int requiredGeneralCredits;

    private int requiredMSC;

    private int requiredBSM;

    private int requiredCourses;

    @ManyToMany
    @JoinTable(
            name = "curriculum_course",
            joinColumns = @JoinColumn(name = "curriculum_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courses = new ArrayList<>();

    @OneToMany(mappedBy = "curriculum")
    private List<GraduationRequirement> graduationRequirements = new ArrayList<>();

    public void addCourse(Course course) {
        this.courses.add(course);
    }
}
