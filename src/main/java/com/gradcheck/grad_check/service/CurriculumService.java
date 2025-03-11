package com.gradcheck.grad_check.service;

import com.gradcheck.grad_check.domain.Curriculum;
import com.gradcheck.grad_check.dto.CurriculumRequest;
import com.gradcheck.grad_check.dto.CurriculumResponse;
import com.gradcheck.grad_check.repository.CurriculumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CurriculumService {
    private final CurriculumRepository curriculumRepository;

    // 특정 학과 & 입학년도 커리큘럼 조회
    public CurriculumResponse getCurriculum(String department, LocalDate year) {
        Curriculum curriculum = curriculumRepository.findByDepartmentAndAdmissionYear(department, year)
                .orElseThrow(() -> new RuntimeException("해당 커리큘럼을 찾을 수 없습니다."));
        return new CurriculumResponse(curriculum);
    }

    // 모든 커리큘럼 조회
    public List<CurriculumResponse> getAllCurriculums() {
        return curriculumRepository.findAll().stream()
                .map(CurriculumResponse::new)
                .collect(Collectors.toList());
    }

    // 새로운 커리큘럼 등록
    public CurriculumResponse createCurriculum(CurriculumRequest request) {
        Curriculum curriculum = new Curriculum();
        curriculum.setDepartment(request.getDepartment());
        curriculum.setAdmissionYear(request.getAdmissionYear());
        curriculum.setRequiredMajorCredits(request.getRequiredMajorCredits());
        curriculum.setRequiredGeneralCredits(request.getRequiredGeneralCredits());
        curriculum.setRequiredMSC(request.getRequiredMSC());
        curriculum.setRequiredBSM(request.getRequiredBSM());

        Curriculum savedCurriculum = curriculumRepository.save(curriculum);
        return new CurriculumResponse(savedCurriculum);
    }

    // 커리큘럼 업데이트
    public CurriculumResponse updateCurriculum(Long id, CurriculumRequest request) {
        Curriculum curriculum = curriculumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 커리큘럼을 찾을 수 없습니다."));

        curriculum.setDepartment(request.getDepartment());
        curriculum.setAdmissionYear(request.getAdmissionYear());
        curriculum.setRequiredMajorCredits(request.getRequiredMajorCredits());
        curriculum.setRequiredGeneralCredits(request.getRequiredGeneralCredits());
        curriculum.setRequiredMSC(request.getRequiredMSC());
        curriculum.setRequiredBSM(request.getRequiredBSM());

        Curriculum updatedCurriculum = curriculumRepository.save(curriculum);
        return new CurriculumResponse(updatedCurriculum);
    }

    // 커리큘럼 삭제
    public void deleteCurriculum(Long id) {
        curriculumRepository.deleteById(id);
    }
}
