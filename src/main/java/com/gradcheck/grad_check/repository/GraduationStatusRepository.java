package com.gradcheck.grad_check.repository;

import com.gradcheck.grad_check.domain.GraduationStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GraduationStatusRepository extends JpaRepository<GraduationStatus, Long> {

    Optional<GraduationStatus> findByMemberId(Long memberId);

    @Modifying
    @Transactional
    @Query("UPDATE GraduationStatus g SET g.graduationThesisStatus = :thesisStatus, " +
            "g.humanRightsEducationCompleted = :educationCompleted WHERE g.memberId = :memberId")
    void updateGraduationStatus(@Param("memberId") Long memberId,
                                @Param("thesisStatus") boolean thesisStatus,
                                @Param("educationCompleted") boolean educationCompleted);
}
