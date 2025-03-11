package com.gradcheck.grad_check.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class CurriculumRequest {

    @JsonProperty("department")
    private String department;

    @JsonProperty("admission_year")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate admissionYear;

    @JsonProperty("required_major_credits")
    private int requiredMajorCredits;

    @JsonProperty("required_general_credits")
    private int requiredGeneralCredits;

    @JsonProperty("requiredmsc")
    private int requiredMSC;

    @JsonProperty("requiredbsm")
    private int requiredBSM;
}
