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
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true,nullable = false)
    private String email;

    private LocalDate dob;

    private String phoneNo;

    private String address;

    @Column(unique = true)
    private String adhaarNo;

    @Enumerated(EnumType.STRING)
    private BloodGroup bloodGroup;


}
