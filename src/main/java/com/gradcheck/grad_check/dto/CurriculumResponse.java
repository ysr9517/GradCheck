package com.gradcheck.grad_check.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gradcheck.grad_check.domain.Curriculum;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CurriculumResponse {

    private Long id;

    private String department;

    @JsonProperty("admission_year")
    private int admissionYear;

    @JsonProperty("required_major_credits")
    private int requiredMajorCredits;

    @JsonProperty("required_general_credits")
    private int requiredGeneralCredits;

    @JsonProperty("requiredmsc")
    private int requiredMSC;

    @JsonProperty("requiredbsm")
    private int requiredBSM;

    public CurriculumResponse(Curriculum curriculum) {
        this.id = curriculum.getId();
        this.department = curriculum.getDepartment();
        this.admissionYear = curriculum.getAdmissionYear();
        this.requiredMajorCredits = curriculum.getRequiredMajorCredits();
        this.requiredGeneralCredits = curriculum.getRequiredGeneralCredits();
        this.requiredMSC = curriculum.getRequiredMSC();
        this.requiredBSM = curriculum.getRequiredBSM();
    }
}
