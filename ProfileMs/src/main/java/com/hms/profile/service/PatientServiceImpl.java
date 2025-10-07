package com.hms.profile.service;

import com.hms.profile.dto.PatientDto;
import com.hms.profile.entity.Patient;
import com.hms.profile.exception.PatientAlreadyExist;
import com.hms.profile.exception.PatientNotFoundException;
import com.hms.profile.repositries.PatientRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService{

    private final PatientRepo patientRepo;

    @Override
    public void create(Patient patient) {
        if(patientRepo.existsByEmail(patient.getEmail())){
            throw new PatientAlreadyExist("patient already exist");
        }
        patientRepo.save(patient);
    }

    @Override
    public Patient getPatient(Long id) {
       Patient patient =  patientRepo.findById(id).orElseThrow(()-> new PatientNotFoundException(("no patient found with this id")));
       return patient;
    }

    @Override
    public Page<PatientDto> getAllPatients(Pageable pageable) {
        Pageable fixedPageable = PageRequest.of(
                pageable.getPageNumber(),  // current page
                10,                        // size of page
                pageable.getSort()         // preserve sorting
        );
        return patientRepo.findAll(fixedPageable)
                .map(patient -> PatientDto.builder()
                        .id(patient.getId())
                        .name(patient.getName())
                        .email(patient.getEmail())
                        .dob(patient.getDob())
                        .address(patient.getAddress())
                        .phoneNo(patient.getPhoneNo())
                        .bloodGroup(patient.getBloodGroup())
                        .build()
                );
    }

}
