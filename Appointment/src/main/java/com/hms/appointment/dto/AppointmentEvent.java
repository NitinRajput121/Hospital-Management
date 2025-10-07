package com.hms.appointment.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentEvent {

    private String email;

    private LocalDateTime appointmentTime;
}
