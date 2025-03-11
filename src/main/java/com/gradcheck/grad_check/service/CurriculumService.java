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
    public CurriculumResponse getCurriculum(String department, int year) {
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

    public CurriculumResponse createCurriculum(CurriculumRequest request) {
        Curriculum curriculum = request.toEntity();  // DTO에서 변환
        Curriculum savedCurriculum = curriculumRepository.save(curriculum);
        return new CurriculumResponse(savedCurriculum);
    }

    public CurriculumResponse updateCurriculum(Long id, CurriculumRequest request) {
        Curriculum curriculum = curriculumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 커리큘럼을 찾을 수 없습니다."));

        Curriculum updatedCurriculum = request.toEntity();
        curriculumRepository.save(updatedCurriculum);

        return new CurriculumResponse(updatedCurriculum);

    }

    public void deleteCurriculum (Long id){
        curriculumRepository.deleteById(id);
    }
}
