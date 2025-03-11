package com.gradcheck.grad_check.controller;

import com.gradcheck.grad_check.domain.Curriculum;
import com.gradcheck.grad_check.dto.CurriculumRequest;
import com.gradcheck.grad_check.dto.CurriculumResponse;
import com.gradcheck.grad_check.service.CurriculumService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/curriculum")
@RequiredArgsConstructor
public class CurriculumController {
    private final CurriculumService curriculumService;

    // 특정 학과 및 입학년도 커리큘럼 조회
    @GetMapping("/{department}/{year}")
    public ResponseEntity<CurriculumResponse> getCurriculum(
            @PathVariable String department, @PathVariable int year) {
        return ResponseEntity.ok(curriculumService.getCurriculum(department, year));
    }

    // 모든 커리큘럼 조회
    @GetMapping
    public ResponseEntity<List<CurriculumResponse>> getAllCurriculums() {
        return ResponseEntity.ok(curriculumService.getAllCurriculums());
    }

    // 새로운 커리큘럼 등록(관리자 기능)
    @PostMapping
    public ResponseEntity<CurriculumResponse> createCurriculum(@RequestBody CurriculumRequest curriculumRequest) {
        CurriculumResponse curriculumResponse = curriculumService.createCurriculum(curriculumRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(curriculumResponse);
    }

    // 커리큘럼 업데이트
    @PutMapping("/{id}")
    public ResponseEntity<CurriculumResponse> updateCurriculum(
            @PathVariable Long id, @RequestBody CurriculumRequest request) {
        return ResponseEntity.ok(curriculumService.updateCurriculum(id, request));
    }

    // 커리큘럼 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCurriculum(@PathVariable Long id) {
        curriculumService.deleteCurriculum(id);
        return ResponseEntity.noContent().build();
    }
}