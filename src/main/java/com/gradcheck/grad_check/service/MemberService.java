package com.gradcheck.grad_check.service;

import com.gradcheck.grad_check.domain.CompletedCourse;
import com.gradcheck.grad_check.domain.Course;
import com.gradcheck.grad_check.domain.Member;
import com.gradcheck.grad_check.dto.CourseDto;
import com.gradcheck.grad_check.dto.JwtToken;
import com.gradcheck.grad_check.dto.MemberDTO;
import com.gradcheck.grad_check.dto.SignUpDTO;
import com.gradcheck.grad_check.repository.MemberRepository;
import com.gradcheck.grad_check.security.JwtTokenProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public JwtToken signIn(String username, String password) {
        // 1. username + password 를 기반으로 Authentication 객체 생성
        // 이때 authentication 은 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        // 2. 실제 검증. authenticate() 메서드를 통해 요청된 Member 에 대한 검증 진행
        // authenticate 메서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);

        return jwtToken;
    }

    @Transactional
    public MemberDTO signUp(SignUpDTO signUpDTO) {
        if (memberRepository.existsByUsername(signUpDTO.getUsername())) {
            throw new IllegalArgumentException("이미 사용 중인 사용자 이름입니다.");
        }
        // Password 암호화
        String encodedPassword = passwordEncoder.encode(signUpDTO.getPassword());
        //List<String> roles = new ArrayList<>();
        //roles.add("USER");  // USER 권한 부여
        return MemberDTO.toDTO(memberRepository.save(signUpDTO.toEntity(encodedPassword)));
    }

    //사용자 정보 수정
    public MemberDTO updateMember(Long id,MemberDTO memberdto) {
        Member member = memberRepository.findById(id)
                .orElseThrow(()->new RuntimeException("해당 id를 찾을 수 없습니다."));
        member = Member.builder()
                .id(member.getId())
                .username(member.getUsername())
                .password(member.getPassword())
                .university(memberdto.getUniversity())
                .department(memberdto.getDepartment())
                .admissionYear(memberdto.getAdmissionYear())
                .expectedGraduationDate(memberdto.getExpectedGraduationDate())
                .isDoubleMajor(memberdto.isDoubleMajor())
                .build();
        Member updateMember = memberRepository.save(member);
        return MemberDTO.toDTO(updateMember);
    }
    //사용자 삭제
    public void deleteMember(Long id) {
        if (!memberRepository.existsById(id)) {
            throw new RuntimeException("사용자를 찾을 수 없습니다.");
        }
        memberRepository.deleteById(id);
    }

    public MemberDTO getMemberById(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()->new IllegalStateException("회원이 존재하지 않습니다."));
        return MemberDTO.toDTO(member);
    }

    public MemberDTO getMemberByUsername(String username) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(()->new IllegalStateException("회원이 존재하지 않습니다."));
        return MemberDTO.toDTO(member);
    }



}
