package com.gradcheck.grad_check.dto;

import com.gradcheck.grad_check.domain.Curriculum;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CurriculumResponse {
    private Long id;
    private String department;
    private LocalDate admissionYear;
    private int requiredMajorCredits;
    private int requiredGeneralCredits;
    private int requiredMSC;
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
