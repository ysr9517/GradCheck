package com.gradcheck.grad_check.dto;

import com.gradcheck.grad_check.domain.GraduationStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GraduationStatusDTO {
    private final int totalCreditsCompleted;
    private final int majorCreditsCompleted;
    private final int generalCreditsCompleted;
    private final int mscCreditsCompleted;
    private final int bsmCreditsCompleted;
    private final int doubleMajorCreditsCompleted;
    private final boolean mandatoryCoursesCompleted;
    private final boolean graduationThesisStatus;
    private final boolean humanRightsEducationCompleted;
    private boolean graduationEligible;

    // 엔티티 → DTO 변환 메서드
    public static GraduationStatusDTO fromEntity(GraduationStatus graduationStatus, boolean graduationEligible) {
        return GraduationStatusDTO.builder()
                .totalCreditsCompleted(graduationStatus.getTotalCreditsCompleted())
                .majorCreditsCompleted(graduationStatus.getMajorCreditsCompleted())
                .generalCreditsCompleted(graduationStatus.getGeneralCreditsCompleted())
                .mscCreditsCompleted(graduationStatus.getMscCreditsCompleted())
                .bsmCreditsCompleted(graduationStatus.getBsmCreditsCompleted())
                .doubleMajorCreditsCompleted(graduationStatus.getDoubleMajorCreditsCompleted())
                .mandatoryCoursesCompleted(graduationStatus.isMandatoryCoursesCompleted())
                .graduationThesisStatus(graduationStatus.isGraduationThesisStatus())
                .humanRightsEducationCompleted(graduationStatus.isHumanRightsEducationCompleted())
                .graduationEligible(graduationEligible)
                .build();
    }
}
