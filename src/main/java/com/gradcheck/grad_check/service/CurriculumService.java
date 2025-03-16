package com.gradcheck.grad_check.service;

import com.gradcheck.grad_check.domain.Course;
import com.gradcheck.grad_check.domain.Curriculum;
import com.gradcheck.grad_check.dto.CurriculumRequest;
import com.gradcheck.grad_check.dto.CurriculumResponse;
import com.gradcheck.grad_check.repository.CourseRepository;
import com.gradcheck.grad_check.repository.CurriculumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CurriculumService {
    private final CurriculumRepository curriculumRepository;
    private final CourseRepository courseRepository;

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

        // 기존 필드 업데이트
        curriculum.setDepartment(request.getDepartment());
        curriculum.setAdmissionYear(request.getAdmissionYear());
        curriculum.setRequiredMajorCredits(request.getRequiredMajorCredits());
        curriculum.setRequiredGeneralCredits(request.getRequiredGeneralCredits());
        curriculum.setRequiredMSC(request.getRequiredMSC());
        curriculum.setRequiredBSM(request.getRequiredBSM());

        List<Course> selectedCourses = request.getCourseIds() != null
                ? courseRepository.findAllById(request.getCourseIds())
                : new ArrayList<>();

        // 기존 과목 초기화 후 새 목록 설정
        curriculum.setCourses(selectedCourses);

        curriculumRepository.save(curriculum);

        return new CurriculumResponse(curriculum);

    }

    // 특정 ID로 커리큘럼 조회
    public CurriculumResponse getCurriculumById(Long id) {
        Curriculum curriculum = curriculumRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 커리큘럼을 찾을 수 없습니다: " + id));

        return new CurriculumResponse(curriculum);
    }

    public Curriculum getCurriculumEntityById(Long id) {
        return curriculumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 커리큘럼을 찾을 수 없습니다."));
    }

    public List<Course> getCoursesByCurriculumId(Long curriculumId) {
        Curriculum curriculum = curriculumRepository.findById(curriculumId)
                .orElseThrow(() -> new RuntimeException("해당 커리큘럼을 찾을 수 없습니다."));

                return curriculum.getCourses();
    }

    public CurriculumResponse updateCurriculumCourses(Long id, List<Long> courseIds) {
        Curriculum curriculum = curriculumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 커리큘럼을 찾을 수 없습니다."));

        // courseIds가 null일 경우 빈 리스트로 초기화
        List<Course> selectedCourses = (courseIds != null) ? courseRepository.findAllById(courseIds) : new ArrayList<>();

        // 기존 과목 리스트를 새 과목 리스트로 변경
        curriculum.setCourses(selectedCourses);

        curriculumRepository.save(curriculum);

        return new CurriculumResponse(curriculum);
    }

    public List<Course> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        System.out.println("📌 현재 DB에 저장된 과목 개수: " + courses.size());
        return courses;
    }

}
