package com.hms.profile.service;

import com.hms.profile.entity.Doctor;

public interface DoctorService {

    void create(Doctor doctor);

    Doctor getDoctor(String email);
}
