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
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/curriculum")
@RequiredArgsConstructor
public class CurriculumController {
    private final CurriculumService curriculumService;
    private final CourseRepository courseRepository;
    private final CurriculumRepository curriculumRepository;

    @GetMapping("/{department}/{year}")
    public String getCurriculum(@PathVariable String department, @PathVariable int year, Model model) {
        CurriculumResponse curriculum = curriculumService.getCurriculum(department, year);
        model.addAttribute("curriculum", curriculum);
        return "curriculum-detail";
    }

    @GetMapping
    public String getAllCurriculums(Model model) {
        List<CurriculumResponse> curriculums = curriculumService.getAllCurriculums();
        model.addAttribute("curriculums", curriculums);
        return "curriculum";
    }

    @PostMapping
    public String createCurriculum(@RequestParam String department,
                                   @RequestParam int admissionYear,
                                   @RequestParam int requiredMajorCredits,
                                   @RequestParam int requiredGeneralCredits,
                                   @RequestParam int requiredMSC,
                                   @RequestParam int requiredBSM) {
        CurriculumResponse curriculumResponse = CurriculumResponse.builder()
                .department(department)
                .admissionYear(admissionYear)
                .requiredMajorCredits(requiredMajorCredits)
                .requiredGeneralCredits(requiredGeneralCredits)
                .requiredMSC(requiredMSC)
                .requiredBSM(requiredBSM)
                .build();
        curriculumService.createCurriculum(curriculumResponse);
        return "redirect:/main";
    }

    @GetMapping("/{id}/view")
    public String viewCurriculum(@PathVariable Long id, Model model) {
        List<Course> courses = curriculumService.getCoursesByCurriculumId(id);
        model.addAttribute("courses", courses);
        return "curriculum-view";
    }

    @GetMapping("/{id}/edit")
    public String editCurriculum(@PathVariable Long id, Model model) {
        Curriculum curriculum = curriculumService.getCurriculumEntityById(id);
        List<Course> allCourses = curriculumService.getAllCourses();
        List<Long> curriculumCourseIds = curriculum.getCourses()
                .stream()
                .map(Course::getId)
                .collect(Collectors.toList());
        model.addAttribute("curriculum", curriculum);
        model.addAttribute("allCourses", allCourses);
        model.addAttribute("curriculumCourseIds", curriculumCourseIds);
        return "curriculum-edit";
    }

    @PostMapping("/{id}/edit")
    public String updateCurriculumEdit(@PathVariable Long id, @RequestParam(required = false) List<Long> courseIds) {
        Curriculum existingCurriculum = curriculumService.getCurriculumEntityById(id);
        curriculumService.updateCurriculumCourses(id, courseIds != null ? courseIds : new ArrayList<>());
        return "redirect:/main";
    }

    @GetMapping("/{id}/edit-info")
    public String editCurriculumInfo(@PathVariable Long id, Model model) {
        Curriculum curriculum = curriculumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 커리큘럼을 찾을 수 없습니다."));
        model.addAttribute("curriculum", curriculum);
        return "curriculum-edit-info";
    }

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
        curriculum.setDepartment(department);
        curriculum.setAdmissionYear(admissionYear);
        curriculum.setRequiredMajorCredits(requiredMajorCredits);
        curriculum.setRequiredGeneralCredits(requiredGeneralCredits);
        curriculum.setRequiredMSC(requiredMSC);
        curriculum.setRequiredBSM(requiredBSM);
        curriculumRepository.save(curriculum);
        return "redirect:/main";
    }

    @PutMapping("/{id}/courses")
    public ResponseEntity<CurriculumResponse> updateCurriculumCourses(@PathVariable Long id,
                                                                      @RequestBody(required = false) List<Long> courseIds) {
        Curriculum existingCurriculum = curriculumService.getCurriculumEntityById(id);
        if (existingCurriculum == null) {
            return ResponseEntity.notFound().build();
        }
        if (courseIds == null) {
            courseIds = new ArrayList<>();
        }
        CurriculumRequest request = new CurriculumRequest();
        request.setDepartment(existingCurriculum.getDepartment());
        request.setAdmissionYear(existingCurriculum.getAdmissionYear());
        request.setRequiredMajorCredits(existingCurriculum.getRequiredMajorCredits());
        request.setRequiredGeneralCredits(existingCurriculum.getRequiredGeneralCredits());
        request.setRequiredMSC(existingCurriculum.getRequiredMSC());
        request.setRequiredBSM(existingCurriculum.getRequiredBSM());
        curriculumService.updateCurriculum(id, request);
        CurriculumResponse updatedCurriculum = curriculumService.updateCurriculumCourses(id, courseIds);
        return ResponseEntity.ok(updatedCurriculum);
    }

    @GetMapping("/main")
    public String mainPage(Model model) {
        List<CurriculumResponse> curriculums = curriculumService.getAllCurriculums();
        model.addAttribute("curriculums", curriculums);
        return "main";
    }
}
