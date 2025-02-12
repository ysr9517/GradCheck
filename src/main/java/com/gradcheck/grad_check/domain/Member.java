package com.gradcheck.grad_check.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.time.LocalDate;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="members")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 고유 식별자

    @Column(nullable = false, unique = true)
    private String password;  // 비밀번호

    @Column(nullable = false)
    private String university;  // 대학교

    @Column(nullable = false)
    private String department;  // 학과

    @Column(nullable = false)
    private int admissionYear;  // 입학년도

    @Column(nullable = false)
    private LocalDate expectedGraduationDate;  // 졸업 예정일

}
