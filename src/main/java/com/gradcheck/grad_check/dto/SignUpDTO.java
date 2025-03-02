package com.gradcheck.grad_check.dto;

import com.gradcheck.grad_check.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpDTO {
    private String username;
    private String password;
    private String university;
    private String department;
    private int admissonYear;
    private LocalDate expectedGraduationDate;

    public Member toEntity(String encodedPassword) {

        return Member.builder()
                .username(username)
                .password(encodedPassword)
                .university(university)
                .department(department)
                .admissionYear(admissonYear)
                .expectedGraduationDate(expectedGraduationDate)
                .build();
    }
}
