package com.gradcheck.grad_check.service;

import com.gradcheck.grad_check.domain.Member;
import com.gradcheck.grad_check.dto.MemberDTO;
import com.gradcheck.grad_check.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;
    //private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public String signIn(MemberDTO memberdto) {
        Member member = memberRepository.findByUsername(memberdto.getUsername())
                .orElseThrow(()->new IllegalStateException("회원이 존재하지 않습니다."));
        if(!passwordEncoder.matches(memberdto.getPassword(),member.getPassword())){
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        return "토큰 넣어주세용";
    }

    @Transactional
    public MemberDTO singUp(MemberDTO memberDto ) {
        if (memberRepository.existsByUsername(memberDto.getUsername())) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
        Member member = Member.builder()
                .username(memberDto.getUsername())
                .password(passwordEncoder.encode(memberDto.getPassword()))
                .university(memberDto.getUniversity())
                .department(memberDto.getDepartment())
                .admissionYear(memberDto.getAdmissionYear())
                .expectedGraduationDate(memberDto.getExpectedGraduationDate())
                .isDoubleMajor(memberDto.isDoubleMajor())
                .build();
        return MemberDTO.fromEntity(memberRepository.save(member));
    }

    //사용자 정보 조회
    public MemberDTO getMemberByUsername(String username) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("해당 id 찾을 수 없음"));
        return MemberDTO.fromEntity(member);
    }

    //사용자 정보 수정
    public MemberDTO updateMember(Long id,MemberDTO memberdto) {
        Member member = memberRepository.findById(id)
                .orElseThrow(()->new RuntimeException("해당 id를 찾을 수 없습니다."));
        member = Member.builder()
                .id(member.getId())
                .username(memberdto.getUsername())
                .password(member.getPassword())
                .university(memberdto.getUniversity())
                .department(memberdto.getDepartment())
                .admissionYear(memberdto.getAdmissionYear())
                .expectedGraduationDate(memberdto.getExpectedGraduationDate())
                .isDoubleMajor(memberdto.isDoubleMajor())
                .build();
        Member updateMember = memberRepository.save(member);
        return MemberDTO.fromEntity(updateMember);
    }
    //사용자 삭제
    public void deleteMember(Long id) {
        if (!memberRepository.existsById(id)) {
            throw new RuntimeException("사용자를 찾을 수 없습니다.");
        }
        memberRepository.deleteById(id);
    }

    //ID로 조회 테스트용
    public MemberDTO findOne(Long memberId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()-> new RuntimeException("해당 id 찾을 수 없음"));
        return MemberDTO.fromEntity(member);
    }
}
