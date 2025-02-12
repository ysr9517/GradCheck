package com.gradcheck.grad_check.service;

import com.gradcheck.grad_check.dto.MemberDTO;
import com.gradcheck.grad_check.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public void signIn(MemberDTO memberdto) {
        memberRepository.signIn(memberdto);
    }

    public void singUp(MemberDTO memberdto) {
        memberRepository.signUp(memberdto);
    }
}
