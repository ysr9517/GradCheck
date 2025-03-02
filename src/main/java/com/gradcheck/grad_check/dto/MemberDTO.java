package com.gradcheck.grad_check.dto;

import com.gradcheck.grad_check.domain.Member;
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
@Builder
public class MemberDTO {
    private Long id;  // 고유 식별자

    private String password;  // 비밀번호

    private String username;

    private String university;  // 대학교

    private String department;  // 학과

    private int admissionYear;  // 입학년도

    private LocalDate expectedGraduationDate;  // 졸업 예정일

    static public MemberDTO toDTO(Member member) {
        return MemberDTO.builder()
                .id(member.getId())
                .username(member.getUsername())
                .university(member.getUniversity())
                .department(member.getDepartment())
                .admissionYear(member.getAdmissionYear())
                .expectedGraduationDate(member.getExpectedGraduationDate())
                .build();
    }

    public Member toEntity() {
        return Member.builder()
                .id(id)
                .username(username)
                .university(university)
                .department(department)
                .admissionYear(admissionYear)
                .expectedGraduationDate(expectedGraduationDate)
                .build();
    }
}
