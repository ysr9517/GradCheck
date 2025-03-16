package com.gradcheck.grad_check.repository;

import com.gradcheck.grad_check.domain.Course;
import com.gradcheck.grad_check.domain.Curriculum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CurriculumRepository extends JpaRepository<Curriculum, Integer> {
    public Optional<Curriculum> findByDepartmentAndAdmissionYear(String department, int admissionYear);

    public Optional<Curriculum> findById(Long id);

    public Optional<Curriculum> deleteById(Long id);

    @Query("SELECT c.courses FROM Curriculum c WHERE c.id = :curriculumId")
    List<Course> findCoursesByCurriculumId(@Param("curriculumId") Long curriculumId);
}