package com.gradcheck.grad_check.controller;

import com.gradcheck.grad_check.domain.Member;
import com.gradcheck.grad_check.dto.MemberDTO;
import com.gradcheck.grad_check.service.MemberService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Getter
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberEditController {
    private final MemberService memberService;

    @GetMapping("/{username}")
    public String getUser(@PathVariable String username, Model model) {
        MemberDTO memberDto = memberService.getMemberByUsername(username);
        model.addAttribute("memberDto", memberDto);
        return "userPage";
    }

    @PostMapping("/{memberName}")
    public String editUser(@PathVariable("memberName") String memberName,
                           @ModelAttribute("memberDto") @Valid MemberDTO memberDto) {
        MemberDTO member = memberService.getMemberByUsername(memberName);
        memberService.updateMember(member.getId(), memberDto);
        return "redirect:/main";
    }

}
