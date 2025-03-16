package com.gradcheck.grad_check.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gradcheck.grad_check.domain.Curriculum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class CurriculumRequest {

    @JsonProperty("department")
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

    private List<Long> courseIds;

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
