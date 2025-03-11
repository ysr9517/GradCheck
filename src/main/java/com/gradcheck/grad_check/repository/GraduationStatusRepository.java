package com.gradcheck.grad_check.repository;

import com.gradcheck.grad_check.domain.GraduationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GraduationStatusRepository extends JpaRepository<GraduationStatus, Long> {

    // Member 엔티티와 연관된 GraduationStatus를 memberId로 조회하는 메서드
    Optional<GraduationStatus> findByMemberId(Long memberId);
}
