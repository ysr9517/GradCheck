package com.gradcheck.grad_check.domain;

import com.gradcheck.grad_check.dto.MemberDTO;
import jakarta.persistence.*;
import lombok.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="member")
public class Member implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", updatable = false, unique = true, nullable = false)
    private Long id;  // 고유 식별자

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String password;  // 비밀번호

    @Column(nullable = false)
    private String university;  // 대학교

    @Column(nullable = false)
    private String department;  // 학과

    @Column(nullable = false)
    private int admissionYear;  // 입학년도

    @Column(nullable = false)
    private LocalDate expectedGraduationDate;  // 졸업 예정일

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> "ROLE_USER");
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    @Column(nullable = false)
    private boolean isDoubleMajor = false;

    @OneToMany(mappedBy = "member")
    private List<CompletedCourse> completedCourses = new ArrayList<>();

    @OneToOne(mappedBy = "member")
    private GraduationStatus graduationStatus;

    @OneToMany(mappedBy = "memberGraduation")
    private List<GraduationCertification> graduationCertifications = new ArrayList<>();


}
