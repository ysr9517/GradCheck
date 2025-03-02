package com.gradcheck.grad_check.controller;

import com.gradcheck.grad_check.domain.Member;
import com.gradcheck.grad_check.dto.MemberDTO;
import com.gradcheck.grad_check.service.MemberService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Getter
@Setter
@Controller
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
//    private final JwtUtil jwtUtil;
//  토근 들어오면 거시기
//    @PostMapping("/signIn")
//    public ResponseEntity<TokenResponse> signIn(MemberDTO memberdto){
//        memberService.signIn(memberdto);
//        return ResponseEntity.ok(new TokenResponse(token));
//    }

    @PostMapping("/signUp")
    public ResponseEntity<MemberDTO> signUp(@Valid @RequestBody MemberDTO memberDTO){
        MemberDTO memberDto = memberService.singUp(memberDTO);
        return ResponseEntity.ok(memberDto);
    }

    @GetMapping("/me")
    public ResponseEntity<MemberDTO> getCurrentUser(Principal principal) {
        MemberDTO memberDto = memberService.getMemberByUsername(principal.getName());
        return ResponseEntity.ok(memberDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberDTO> updateMember(@PathVariable Long id,@ModelAttribute MemberDTO memberDTO){
        return ResponseEntity.ok( memberService.updateMember(id, memberDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id){
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }

}
