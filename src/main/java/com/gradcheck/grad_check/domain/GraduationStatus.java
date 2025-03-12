package com.gradcheck.grad_check.domain;

import com.gradcheck.grad_check.dto.GraduationStatusDTO;
import com.gradcheck.grad_check.dto.MemberDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GraduationStatus {
    @Id
    @Column(name = "member_id")
    private Long memberId;

    private int totalCreditsCompleted;

    private int majorCreditsCompleted;

    private int generalCreditsCompleted;

    private int mscCreditsCompleted;

    private int bsmCreditsCompleted;

    private int doubleMajorCreditsCompleted;

    private boolean mandatoryCoursesCompleted;

    private boolean graduationThesisStatus;

    private boolean humanRightsEducationCompleted;

    private boolean isGraduationEligible;

    public GraduationStatusDTO toDTO(GraduationStatus graduationStatus) {
        return GraduationStatusDTO.builder()
                .memberId(graduationStatus.getMemberId())
                .totalCreditsCompleted(graduationStatus.getTotalCreditsCompleted())
                .majorCreditsCompleted(graduationStatus.getMajorCreditsCompleted())
                .generalCreditsCompleted(graduationStatus.getGeneralCreditsCompleted())
                .mscCreditsCompleted(graduationStatus.getMscCreditsCompleted())
                .bsmCreditsCompleted(graduationStatus.getBsmCreditsCompleted())
                .doubleMajorCreditsCompleted(graduationStatus.getDoubleMajorCreditsCompleted())
                .mandatoryCoursesCompleted(graduationStatus.isMandatoryCoursesCompleted())
                .graduationThesisStatus(graduationStatus.isGraduationThesisStatus())
                .humanRightsEducationCompleted(graduationStatus.isHumanRightsEducationCompleted())
                .isGraduationEligible(graduationStatus.isGraduationEligible())
                .build();
    }
}
