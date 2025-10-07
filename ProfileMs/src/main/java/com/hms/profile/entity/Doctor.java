package com.hms.profile.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private LocalDate dob;

    private String phoneNo;

    private String address;

    @Column(unique = true)
    private String licence;

    @Enumerated(EnumType.STRING)
    private BloodGroup bloodGroup;

    private String deparment;

    private String specialization;

    private Integer experience;
}
