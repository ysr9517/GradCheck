package com.gradcheck.grad_check.repository;

import com.gradcheck.grad_check.domain.CompletedCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompletedCourseRepository extends JpaRepository<CompletedCourse, Long> {
    Optional<CompletedCourse> findByMemberIdAndCourseId(Long memberId, Long courseId);
    List<CompletedCourse> findByMemberId(Long memberId);

    boolean existsByMemberIdAndCourseId(Long memberId, Long courseId);
}
