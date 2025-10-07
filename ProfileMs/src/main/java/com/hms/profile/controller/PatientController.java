package com.hms.profile.controller;

import com.hms.profile.dto.PatientDto;
import com.hms.profile.entity.Patient;
import com.hms.profile.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile/patient")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @PostMapping("/create")
    public ResponseEntity<String> createPatient(@Validated @RequestBody  Patient patient){
           patientService.create(patient);
        return ResponseEntity.ok("pateint created");
    }

    @GetMapping("/get")
    public ResponseEntity<Patient> getPatient(@RequestParam Long id){
        Patient patient = patientService.getPatient(id);
        return ResponseEntity.ok(patient);
    }

    @GetMapping("/getAll")
    public ResponseEntity<Page<PatientDto>> getAllPatients(Pageable pageable) {
        Page<PatientDto> patientDtos =  patientService.getAllPatients(pageable);
        return ResponseEntity.ok(patientDtos);
    }



}
