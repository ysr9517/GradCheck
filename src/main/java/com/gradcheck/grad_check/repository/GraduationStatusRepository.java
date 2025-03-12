package com.gradcheck.grad_check.repository;

import com.gradcheck.grad_check.domain.GraduationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GraduationStatusRepository extends JpaRepository<GraduationStatus, Long> {

    Optional<GraduationStatus> findByMemberId(Long memberId);
}
