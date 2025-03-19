package com.gradcheck.grad_check.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gradcheck.grad_check.domain.Course;
import com.gradcheck.grad_check.domain.Curriculum;
import com.gradcheck.grad_check.repository.CourseRepository;
import lombok.Getter;

import java.util.List;

@Getter
public class CurriculumRequest {

    private String department;

    private int admissionYear;

    private int requiredMajorCredits;

    private int requiredGeneralCredits;

    private int requiredMSC;

    private int requiredBSM;

    private List<Long> courseIds;

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

    public void setCourseIds(List<Long> courseIds) {
        this.courseIds = courseIds;
    }

    public Curriculum toEntity(CourseRepository courseRepository) {
        // CourseRepository를 통해 실제 과목을 DB에서 찾아서 설정
        List<Course> selectedCourses = courseRepository.findAllById(courseIds);

        return Curriculum.builder()
                .department(department)
                .admissionYear(admissionYear)
                .requiredMajorCredits(requiredMajorCredits)
                .requiredGeneralCredits(requiredGeneralCredits)
                .requiredMSC(requiredMSC)
                .requiredBSM(requiredBSM)
                .courses(selectedCourses) // 과목 목록을 추가
                .build();
    }





}
