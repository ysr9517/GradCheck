package com.gradcheck.grad_check.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class GraduationStatus {
    @Id
    @GeneratedValue
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

    private int mandatoryCoursesCompleted;

    private int graduationThesisStatus;

    private int humanRightsEducationCompleted;

    private int doubleMajorCreditsCompleted;

}
