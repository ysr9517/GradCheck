package com.gradcheck.grad_check.controller;

import com.gradcheck.grad_check.domain.GraduationStatus;
import com.gradcheck.grad_check.dto.GraduationStatusDTO;
import com.gradcheck.grad_check.service.GraduationStatusService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Getter
@Setter
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/graduation")
public class GraduationController {

    private final GraduationStatusService graduationStatusService;

    @GetMapping("/status/{memberId}")
    public ResponseEntity<GraduationStatusDTO> getGraduationStatus(@PathVariable Long memberId) {
        GraduationStatusDTO graduationStatusDTO = graduationStatusService.checkGraduationStatus(memberId);
        return ResponseEntity.status(HttpStatus.OK).body(graduationStatusDTO);
    }

}
