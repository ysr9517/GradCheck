package com.gradcheck.grad_check.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gradcheck.grad_check.domain.Curriculum;
import lombok.Getter;

import java.util.List;

@Getter
public class CurriculumRequest {

    @JsonProperty("department")
    private String department;

    @JsonProperty("admissionYear")
    private int admissionYear;

    @JsonProperty("requiredMajorCredits")
    private int requiredMajorCredits;

    @JsonProperty("requiredGeneralCredits")
    private int requiredGeneralCredits;

    @JsonProperty("requiredMSC")
    private int requiredMSC;

    @JsonProperty("requiredBSM")
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

    public Curriculum toEntity() {
        return Curriculum.builder()
                .department(department)
                .admissionYear(admissionYear)
                .requiredMajorCredits(requiredMajorCredits)
                .requiredGeneralCredits(requiredGeneralCredits)
                .requiredMSC(requiredMSC)
                .requiredBSM(requiredBSM)
                .build();
    }

}
