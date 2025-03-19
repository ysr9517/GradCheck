package com.gradcheck.grad_check.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gradcheck.grad_check.domain.Curriculum;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CurriculumResponse {

    private Long id;

    private String department;


    private int admissionYear;


    private int requiredMajorCredits;


    private int requiredGeneralCredits;


    private int requiredMSC;


    private int requiredBSM;

    public static CurriculumResponse from(Curriculum curriculum) {
        return CurriculumResponse.builder()
                .id(curriculum.getId())
                .department(curriculum.getDepartment())
                .admissionYear(curriculum.getAdmissionYear())
                .requiredMajorCredits(curriculum.getRequiredMajorCredits())
                .requiredGeneralCredits(curriculum.getRequiredGeneralCredits())
                .requiredMSC(curriculum.getRequiredMSC())
                .requiredBSM(curriculum.getRequiredBSM())
                .build();
    }
}
