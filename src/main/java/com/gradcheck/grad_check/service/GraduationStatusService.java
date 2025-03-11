package com.gradcheck.grad_check.service;

import com.gradcheck.grad_check.domain.Curriculum;
import com.gradcheck.grad_check.domain.GraduationStatus;
import com.gradcheck.grad_check.domain.Member;
import com.gradcheck.grad_check.dto.GraduationStatusDTO;
import com.gradcheck.grad_check.repository.CurriculumRepository;
import com.gradcheck.grad_check.repository.GraduationStatusRepository;
import com.gradcheck.grad_check.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GraduationStatusService {
    private final MemberRepository memberRepository;
    private final GraduationStatusRepository graduationStatusRepository;
    private final CurriculumRepository curriculumRepository;

    public GraduationStatusDTO checkGraduationStatus(Long memberId) {
        // memberId로 Member 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));

        // GraduationStatus 조회
        GraduationStatus graduationStatus = graduationStatusRepository.findByMemberId(memberId)
                .orElseThrow(() -> new IllegalArgumentException("졸업 상태가 존재하지 않습니다."));

        // 해당 학과의 커리큘럼 조회
        Curriculum curriculum = curriculumRepository.findByDepartmentAndAdmissionYear(
                        member.getDepartment(), member.getAdmissionYear())
                .orElseThrow(() -> new IllegalArgumentException("학과에 해당하는 커리큘럼이 존재하지 않습니다."));

        // 졸업 요건 충족 여부 판단
        boolean isGraduationEligible = checkGraduationEligibility(graduationStatus, curriculum);

        // DTO 변환 후 반환
        return GraduationStatusDTO.fromEntity(graduationStatus, isGraduationEligible);
    }

    // 졸업 요건 충족 여부 체크
    private boolean checkGraduationEligibility(GraduationStatus graduationStatus, Curriculum curriculum) {
        boolean isCreditsSufficient = graduationStatus.getTotalCreditsCompleted() >= curriculum.getRequiredMajorCredits()
                && graduationStatus.getGeneralCreditsCompleted() >= curriculum.getRequiredGeneralCredits()
                && graduationStatus.getMscCreditsCompleted() >= curriculum.getRequiredMSC()
                && graduationStatus.getBsmCreditsCompleted() >= curriculum.getRequiredBSM();

        boolean isCoursesCompleted = graduationStatus.isMandatoryCoursesCompleted();
        boolean isThesisSubmitted = graduationStatus.isGraduationThesisStatus();
        boolean isHumanRightsCompleted = graduationStatus.isHumanRightsEducationCompleted();

        return isCreditsSufficient && isCoursesCompleted && isThesisSubmitted && isHumanRightsCompleted;
    }
}
