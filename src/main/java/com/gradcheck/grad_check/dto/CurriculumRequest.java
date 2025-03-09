package com.gradcheck.grad_check.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class CurriculumRequest {
    private String department;
    private LocalDate admissionYear;
    private int requiredMajorCredits;
    private int requiredGeneralCredits;
    private int requiredMSC;
    private int requiredBSM;
}
