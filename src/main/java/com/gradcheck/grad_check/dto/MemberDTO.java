package com.gradcheck.grad_check.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {
    private Long id;  // 고유 식별자

    private String password;  // 비밀번호

    private String university;  // 대학교

    private String department;  // 학과

    private int admissionYear;  // 입학년도

    private LocalDate expectedGraduationDate;  // 졸업 예정일
}
