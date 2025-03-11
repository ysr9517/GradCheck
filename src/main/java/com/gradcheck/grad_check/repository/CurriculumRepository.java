package com.gradcheck.grad_check.repository;

import com.gradcheck.grad_check.domain.Curriculum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurriculumRepository extends JpaRepository<Curriculum, Integer> {
    public Optional<Curriculum> findByDepartmentAndAdmissionYear(String department, int admissionYear);
}
