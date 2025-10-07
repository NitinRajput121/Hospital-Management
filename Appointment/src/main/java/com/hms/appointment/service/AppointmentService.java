package com.hms.appointment.service;

import com.hms.appointment.dto.AppointmentReaponseDto;
import com.hms.appointment.dto.AppointmentRequestDto;
import com.hms.appointment.entity.Appointment;

public interface AppointmentService {

    void create(AppointmentRequestDto appointmentRequestDto);

    AppointmentReaponseDto changeStatus(AppointmentRequestDto appointmentRequestDto,Long id);
}
