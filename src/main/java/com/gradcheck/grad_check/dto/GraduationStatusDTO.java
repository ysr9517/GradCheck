package com.gradcheck.grad_check.dto;

import com.gradcheck.grad_check.domain.GraduationStatus;
import com.gradcheck.grad_check.domain.Member;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GraduationStatusDTO {
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
    private boolean graduationCertificationRequirements;
    private boolean isGraduationEligible;

}
