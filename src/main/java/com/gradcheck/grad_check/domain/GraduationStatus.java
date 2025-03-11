package com.gradcheck.grad_check.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class GraduationStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "graduation_status_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    private int totalCreditsCompleted;
    private int majorCreditsCompleted;
    private int generalCreditsCompleted;
    private int mscCreditsCompleted;
    private int bsmCreditsCompleted;
    private int doubleMajorCreditsCompleted;

    private boolean mandatoryCoursesCompleted; // 필수 과목 이수 여부
    private boolean graduationThesisStatus; // 졸업 논문 제출 여부
    private boolean humanRightsEducationCompleted; // 인권 교육 이수 여부
}
