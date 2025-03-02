package com.example.graduation.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String university;

    @Column(nullable = false)
    private String department;

    @Column(nullable = false)
    private int admissionYear;

    @Column(nullable = false)
    private int expectedGraduationDate;

    @Column(nullable = false)
    private Boolean isDoubleMajor;
}
