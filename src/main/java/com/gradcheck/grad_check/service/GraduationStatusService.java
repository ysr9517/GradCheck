package com.gradcheck.grad_check.service;

import com.gradcheck.grad_check.domain.*;
import com.gradcheck.grad_check.dto.GraduationStatusDTO;
import com.gradcheck.grad_check.repository.CompletedCourseRepository;
import com.gradcheck.grad_check.repository.CurriculumRepository;
import com.gradcheck.grad_check.repository.GraduationStatusRepository;
import com.gradcheck.grad_check.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GraduationStatusService {
    private final MemberRepository memberRepository;
    private final GraduationStatusRepository graduationStatusRepository;
    private final CurriculumRepository curriculumRepository;
    private final CompletedCourseRepository completedCourseRepository;

    @Transactional
    public GraduationStatusDTO createAndUpdateGraduationStatus(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Curriculum curriculum = curriculumRepository.findByDepartmentAndAdmissionYear(member.getDepartment(),member.getAdmissionYear())
                .orElseThrow(() -> new IllegalArgumentException("해당 학과의 졸업 요건을 찾을 수 없습니다."));


        List<CompletedCourse> completedCourses = completedCourseRepository.findByMemberId(memberId);

        int totalCredits = 0;
        int majorCredits = 0;
        int generalCredits = 0;
        int mscCredits = 0;
        int bsmCredits = 0;

        for (CompletedCourse completedCourse : completedCourses) {
            Course course = completedCourse.getCourse();
            totalCredits += course.getCredit();

            if ("전공".equals(course.getCategory())) {
                majorCredits += course.getCredit();
            }
            if ("교양".equals(course.getCategory())) {
                generalCredits += course.getCredit();
            }
            if ("MSC".equals(course.getCategory())) {
                mscCredits += course.getCredit();
            }
            if ("BSM".equals(course.getCategory())) {
                bsmCredits += course.getCredit();
            }
        }

        boolean isEligible = totalCredits >= curriculum.getRequiredMajorCredits()+curriculum.getRequiredGeneralCredits()
                && majorCredits >= curriculum.getRequiredMajorCredits()
                && generalCredits >= curriculum.getRequiredGeneralCredits()
                && mscCredits >= curriculum.getRequiredMSC()
                && bsmCredits >= curriculum.getRequiredBSM();

        GraduationStatus graduationStatus = GraduationStatus.builder()
                .memberId(memberId)
                .totalCreditsCompleted(totalCredits)
                .majorCreditsCompleted(majorCredits)
                .generalCreditsCompleted(generalCredits)
                .mscCreditsCompleted(mscCredits)
                .bsmCreditsCompleted(bsmCredits)
                .mandatoryCoursesCompleted(false) //추가 로직 필요
                .graduationThesisStatus(false) // 추가 로직 필요
                .humanRightsEducationCompleted(false) // 추가 로직 필요
                .isGraduationEligible(isEligible)
                .build();

        graduationStatusRepository.save(graduationStatus);
        return graduationStatus.toDTO(graduationStatus);
    }
}
