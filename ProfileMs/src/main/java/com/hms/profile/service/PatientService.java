package com.hms.profile.service;

import com.hms.profile.dto.PatientDto;
import com.hms.profile.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PatientService {

     void create(Patient patient);

     Patient getPatient(Long id);

     Page<PatientDto> getAllPatients(Pageable pageable);


}
