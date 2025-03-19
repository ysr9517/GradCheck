package com.gradcheck.grad_check.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.ui.Model;

import java.util.*;

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


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "curriculum_course",
            joinColumns = @JoinColumn(name = "curriculum_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courses = new ArrayList<>();

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Curriculum curriculum = (Curriculum) obj;
        return Objects.equals(id, curriculum.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @OneToMany(mappedBy = "curriculum")
    private List<GraduationRequirement> graduationRequirements = new ArrayList<>();

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setAdmissionYear(int admissionYear) {
        this.admissionYear = admissionYear;
    }

    public void setRequiredMajorCredits(int requiredMajorCredits) {
        this.requiredMajorCredits = requiredMajorCredits;
    }

    public void setRequiredGeneralCredits(int requiredGeneralCredits) {
        this.requiredGeneralCredits = requiredGeneralCredits;
    }

    public void setRequiredMSC(int requiredMSC) {
        this.requiredMSC = requiredMSC;
    }

    public void setRequiredBSM(int requiredBSM) {
        this.requiredBSM = requiredBSM;
    }

    // 과목 추가
    public void addCourse(Course course) {
        this.courses.add(course);
    }

    // 과목 삭제
    public void removeCourse(Course course) {
        this.courses.remove(course);
    }

    // 전체 과목 업데이트
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
