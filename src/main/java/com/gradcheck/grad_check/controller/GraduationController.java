package com.gradcheck.grad_check.controller;

import com.gradcheck.grad_check.dto.GraduationStatusDTO;
import com.gradcheck.grad_check.service.GraduationStatusService;
import com.gradcheck.grad_check.service.MemberService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Getter
@Setter
@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/graduation")
public class GraduationController {

    private final GraduationStatusService graduationStatusService;
    private final MemberService memberService;

    @GetMapping("/{memberName}/graduationCheck")
    public String createOrUpdateGraduationStatus(@PathVariable String memberName, Model model) {

        Long memberId = memberService.getMemberByUsername(memberName).getId();
        GraduationStatusDTO graduationStatusDTO = graduationStatusService.createAndUpdateGraduationStatus(memberId);

        System.out.println("isGraduationEligible: " + graduationStatusDTO.isGraduationEligible());
        model.addAttribute("graduationStatus", graduationStatusDTO);
        return "graduationCheck";

    }

    @PostMapping("/{memberName}/graduationCheckUpdate")
    public String updateGraduationStatus(
            @PathVariable String memberName,
            @ModelAttribute("graduationStatus") @Valid GraduationStatusDTO graduationStatusDTO,
            @RequestParam(name = "graduationThesisStatus", defaultValue = "false") boolean graduationThesisStatus,
            @RequestParam(name = "humanRightsEducationCompleted", defaultValue = "false") boolean humanRightsEducationCompleted,
            @RequestParam(name = "graduationCertificationRequirements", defaultValue = "false") boolean graduationCertificationRequirements) {


        Long memberId = memberService.getMemberByUsername(memberName).getId();
        graduationStatusService.updateGraduationStatus(memberId, graduationThesisStatus, humanRightsEducationCompleted, graduationCertificationRequirements);

        return "redirect:/main";
    }

    @GetMapping("/graduationCheck")
    public String showGraduationCheckPage() {
        return "graduationCheck";
    }
}
