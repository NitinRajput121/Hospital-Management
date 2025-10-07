package com.hms.profile.controller;

import com.hms.profile.entity.Doctor;
import com.hms.profile.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile/doctor")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @PostMapping("/create")
    public ResponseEntity<String> createDoctor(@RequestBody Doctor doctor){
        doctorService.create(doctor);
        return ResponseEntity.ok("doctor created");
    }

    @GetMapping("/get")
    public ResponseEntity<Doctor> getDoctor(@RequestParam String email){
        Doctor doctor = doctorService.getDoctor(email);
        return ResponseEntity.ok(doctor);
    }
}
