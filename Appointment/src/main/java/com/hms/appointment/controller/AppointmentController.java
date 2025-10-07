package com.hms.appointment.controller;

import com.hms.appointment.dto.AppointmentReaponseDto;
import com.hms.appointment.dto.AppointmentRequestDto;
import com.hms.appointment.service.AppointmentService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping("/create")

    public ResponseEntity<String> create(@RequestBody AppointmentRequestDto appointmentRequestDto){
        appointmentService.create(appointmentRequestDto);
        return ResponseEntity.ok("your appointment is created and soon notified as it will be confirmed");
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<AppointmentReaponseDto> update(@RequestBody AppointmentRequestDto appointmentRequestDto, @PathVariable("id") Long id){
        AppointmentReaponseDto appointmentReaponseDto =   appointmentService.changeStatus(appointmentRequestDto,id);
        return ResponseEntity.ok(appointmentReaponseDto);
    }




}
