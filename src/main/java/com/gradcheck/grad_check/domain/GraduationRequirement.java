package com.gradcheck.grad_check.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class GraduationRequirement {
    @Id
    @GeneratedValue
    @Column(name = "graduation_requirement_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curriculum_id")
    private Curriculum curriculum;

    //타입 임시
    private String requirementType;
    private String description;
    private String isCompleted;


}
