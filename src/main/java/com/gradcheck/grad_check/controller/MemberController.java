package com.gradcheck.grad_check.controller;

import com.gradcheck.grad_check.dto.MemberDTO;
import com.gradcheck.grad_check.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Getter
@Setter
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signIn")
    public String signIn(MemberDTO memberdto){
        memberService.signIn(memberdto);
        return "signIn";
    }

    @PostMapping("/signUp")
    public String signUp(MemberDTO memberdto){
        memberService.singUp(memberdto);
        return "signUp";
    }
}
