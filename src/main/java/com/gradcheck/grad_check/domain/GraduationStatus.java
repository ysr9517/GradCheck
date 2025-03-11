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

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    private int totalCreditsCompleted;

    private int majorCreditsCompleted;

    private int generalCreditsCompleted;

    private int mscCreditsCompleted;

    private int bsmCreditsCompleted;

    private int doubleMajorCreditsCompleted;

    private boolean mandatoryCoursesCompleted;

    private boolean graduationThesisStatus;

    private boolean humanRightsEducationCompleted;
}
