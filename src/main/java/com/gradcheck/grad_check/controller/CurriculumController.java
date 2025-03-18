package com.gradcheck.grad_check.controller;

import com.gradcheck.grad_check.domain.Course;
import com.gradcheck.grad_check.domain.Curriculum;
import com.gradcheck.grad_check.dto.CurriculumRequest;
import com.gradcheck.grad_check.dto.CurriculumResponse;
import com.gradcheck.grad_check.repository.CourseRepository;
import com.gradcheck.grad_check.repository.CurriculumRepository;
import com.gradcheck.grad_check.service.CurriculumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/curriculum")
@RequiredArgsConstructor
public class CurriculumController {
    private final CurriculumService curriculumService;
    private final CourseRepository courseRepository;
    private final CurriculumRepository curriculumRepository;

    // 특정 학과 및 입학년도 커리큘럼 조회
    @GetMapping("/{department}/{year}")
    public String getCurriculum(
            @PathVariable String department, @PathVariable int year, Model model) {
        CurriculumResponse curriculum = curriculumService.getCurriculum(department, year);
        model.addAttribute("curriculum", curriculum);
        return "curriculum-detail";
    }

    // 모든 커리큘럼 조회
    @GetMapping
    public String getAllCurriculums(Model model) {
        List<CurriculumResponse> curriculums = curriculumService.getAllCurriculums();
        model.addAttribute("curriculums", curriculums);
        return "curriculumPage";
    }

    // 커리큘럼 추가
    @PostMapping
    @ResponseBody // ✅ JSON 응답을 위해 추가!
    public CurriculumResponse createCurriculum(@RequestBody CurriculumRequest request) {
        return curriculumService.createCurriculum(request); // 생성된 커리큘럼 반환
    }

    // 커리큘럼 업데이트 처리
    @PutMapping("/{id}/update")
    public String updateCurriculum(@PathVariable Long id,
                                   @RequestBody CurriculumRequest request) {
        curriculumService.updateCurriculum(id, request);

        return "redirect:/curriculum";
    }

    // 커리큘럼 과목 조회 페이지
    @GetMapping("/{id}/view")
    public String viewCurriculum(@PathVariable Long id, Model model) {
        List<Course> courses = curriculumService.getCoursesByCurriculumId(id);
        model.addAttribute("courses", courses);
        return "curriculum-view";
    }

    // 커리큘럼 과목 수정 페이지
    @GetMapping("/{id}/edit")
    public String editCurriculum(@PathVariable Long id, Model model) {
        Curriculum curriculum = curriculumService.getCurriculumEntityById(id);

        // 📌 서비스에서 모든 과목 가져오기
        List<Course> allCourses = curriculumService.getAllCourses();

        // 📌 현재 커리큘럼에 등록된 과목 ID 목록 추출
        List<Long> curriculumCourseIds = curriculum.getCourses()
                .stream()
                .map(Course::getId)
                .collect(Collectors.toList());

        // 📌 Model에 추가
        model.addAttribute("curriculum", curriculum);
        model.addAttribute("allCourses", allCourses);
        model.addAttribute("curriculumCourseIds", curriculumCourseIds);

        return "curriculum-edit";
    }

    // 업데이트
    @PostMapping("/{id}/edit")
    public String updateCurriculumEdit(@PathVariable Long id,
                                       @RequestParam(required = false) List<Long> courseIds) {
        // 기존 커리큘럼 가져오기
        Curriculum existingCurriculum = curriculumService.getCurriculumEntityById(id);

        // 기존 데이터를 유지하면서 과목만 업데이트
        curriculumService.updateCurriculumCourses(id, courseIds != null ? courseIds : new ArrayList<>());

        return "redirect:/curriculum"; // 저장 후 목록 페이지로 이동
    }



    // 커리큘럼 수정
    @GetMapping("/{id}/edit-info")
    public String editCurriculumInfo(@PathVariable Long id, Model model) {
        Curriculum curriculum = curriculumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 커리큘럼을 찾을 수 없습니다."));

        model.addAttribute("curriculum", curriculum);
        return "curriculum-edit-info"; // 새로 만들 페이지
    }

    // 커리큘럼 정보 업데이트
    @PostMapping("/{id}/update-info")
    public String updateCurriculumInfo(@PathVariable Long id,
                                       @RequestParam String department,
                                       @RequestParam Integer admissionYear,
                                       @RequestParam Integer requiredMajorCredits,
                                       @RequestParam Integer requiredGeneralCredits,
                                       @RequestParam Integer requiredMSC,
                                       @RequestParam Integer requiredBSM) {
        Curriculum curriculum = curriculumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 커리큘럼을 찾을 수 없습니다."));

        // 커리큘럼 정보 업데이트
        curriculum.setDepartment(department);
        curriculum.setAdmissionYear(admissionYear);
        curriculum.setRequiredMajorCredits(requiredMajorCredits);
        curriculum.setRequiredGeneralCredits(requiredGeneralCredits);
        curriculum.setRequiredMSC(requiredMSC);
        curriculum.setRequiredBSM(requiredBSM);

        curriculumRepository.save(curriculum); // 📌 저장

        return "redirect:/curriculum"; // 수정 후 목록으로 이동
    }

    @PutMapping("/{id}/courses")
    public ResponseEntity<CurriculumResponse> updateCurriculumCourses(
            @PathVariable Long id,
            @RequestBody(required = false) List<Long> courseIds // null 허용
    ) {
        // 기존 커리큘럼 가져오기
        Curriculum existingCurriculum = curriculumService.getCurriculumEntityById(id);

        if (existingCurriculum == null) {
            return ResponseEntity.notFound().build(); // 커리큘럼이 없으면 404 응답
        }

        if (courseIds == null) {
            courseIds = new ArrayList<>(); // courseIds가 null이면 빈 리스트로 초기화
        }

        // 기존 커리큘럼의 학과, 입학년도 등 기본 정보 유지하면서 과목 업데이트
        CurriculumRequest request = new CurriculumRequest();
        request.setDepartment(existingCurriculum.getDepartment());
        request.setAdmissionYear(existingCurriculum.getAdmissionYear());
        request.setRequiredMajorCredits(existingCurriculum.getRequiredMajorCredits());
        request.setRequiredGeneralCredits(existingCurriculum.getRequiredGeneralCredits());
        request.setRequiredMSC(existingCurriculum.getRequiredMSC());
        request.setRequiredBSM(existingCurriculum.getRequiredBSM());

        // 기존 정보 유지하면서 업데이트 수행
        curriculumService.updateCurriculum(id, request); // 기본 정보 업데이트
        CurriculumResponse updatedCurriculum = curriculumService.updateCurriculumCourses(id, courseIds); // 과목 업데이트

        return ResponseEntity.ok(updatedCurriculum);
    }


    @GetMapping("/test-courses")
    @ResponseBody
    public List<Course> testGetAllCourses() {
        List<Course> allCourses = curriculumService.getAllCourses();

        System.out.println("📌 테스트: DB에서 불러온 모든 과목 개수: " + allCourses.size());
        for (Course course : allCourses) {
            System.out.println("✅ 과목 ID: " + course.getId() + ", 과목명: " + course.getName());
        }

        return allCourses;
    }

//
}