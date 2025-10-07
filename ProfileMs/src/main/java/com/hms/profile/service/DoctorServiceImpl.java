package com.hms.profile.service;

import com.hms.profile.entity.Doctor;
import com.hms.profile.exception.DoctorNotFoundException;
import com.hms.profile.repositries.DoctorRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService
{

    public final DoctorRepo doctorRepo;

    @Override
    public void create(Doctor doctor) {
           doctorRepo.save(doctor);
    }

    @Override
    public Doctor getDoctor(String email) {
        Doctor doctor  = doctorRepo.findByEmail(email).orElseThrow(()-> new DoctorNotFoundException("no doctor found with this email"));

        return doctor;
    }
}
