package com.gradcheck.grad_check.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class GraduationStatusDTO {
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
