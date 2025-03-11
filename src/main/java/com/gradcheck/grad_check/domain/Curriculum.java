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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Curriculum {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "curriculum_id")
    private Long id;

    private String department;

    private int admissionYear;

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
