package com.hms.appointment.mapper;

import com.hms.appointment.dto.AppointmentReaponseDto;
import com.hms.appointment.dto.AppointmentRequestDto;
import com.hms.appointment.entity.Appointment;
import com.hms.appointment.entity.Status;
import org.springframework.stereotype.Component;

@Component
public class DtoMapper {

    public Appointment convertToEntity(AppointmentRequestDto appointmentRequestDto){

        Appointment appointment = Appointment.builder()
                .id(appointmentRequestDto.getId())
                .patientId(appointmentRequestDto.getPatientId())
                .doctorId(appointmentRequestDto.getDoctorId())
                .email(appointmentRequestDto.getEmail())
                .appointmentTime(appointmentRequestDto.getAppointmentTime())
                .status(Status.PENDING)
                .reason(appointmentRequestDto.getReason())
                .build();

        return appointment;
    }

    public AppointmentReaponseDto convertToResponse(Appointment appointment){
        return AppointmentReaponseDto.builder()
                .id(appointment.getId())
                .patientId(appointment.getPatientId())
                .doctorId(appointment.getDoctorId())
                .email(appointment.getEmail())
                .appointmentTime(appointment.getAppointmentTime())
                .status(appointment.getStatus())
                .reason(appointment.getReason())
                .build();
    }
}
