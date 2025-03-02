package com.gradcheck.grad_check.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class GraduationCertification {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member memberGraduation;

    //임시~~

}
