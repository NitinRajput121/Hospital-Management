package com.hms.profile.dto;

import com.hms.profile.entity.BloodGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientDto {

    private Long id;

    private String name;

    private String email;

    private LocalDate dob;

    private String phoneNo;

    private String address;

    private String adhaarNo;

    private BloodGroup bloodGroup;
}
