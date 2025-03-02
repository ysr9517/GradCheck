package com.gradcheck.grad_check.domain;

import com.gradcheck.grad_check.dto.MemberDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="members")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;  // 고유 식별자

    @Column(nullable = false, unique = true)
    private String username;

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

    @Column(nullable = false)
    private boolean isDoubleMajor = false;

    @OneToMany(mappedBy = "member")
    private List<CompletedCourse> completedCourses = new ArrayList<>();

    @OneToOne(mappedBy = "member")
    private GraduationStatus graduationStatus;

    @OneToMany(mappedBy = "memberGraduation")
    private List<GraduationCertification> graduationCertifications = new ArrayList<>();


}
