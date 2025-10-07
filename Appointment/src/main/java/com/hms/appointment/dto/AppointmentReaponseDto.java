package com.hms.appointment.dto;

import com.hms.appointment.entity.Status;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentReaponseDto {

    private Long id;

    private Long patientId;

    private Long doctorId;

    @Email
    private String email;

    private LocalDateTime appointmentTime;

    private Status status;

    private String reason;
}
