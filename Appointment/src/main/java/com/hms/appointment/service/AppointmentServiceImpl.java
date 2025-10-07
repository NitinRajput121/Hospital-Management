package com.hms.appointment.service;

import com.hms.appointment.dto.AppointmentEvent;
import com.hms.appointment.dto.AppointmentReaponseDto;
import com.hms.appointment.dto.AppointmentRequestDto;
import com.hms.appointment.dto.UserDetailDto;
import com.hms.appointment.entity.Appointment;
import com.hms.appointment.feign.UserDetail;
import com.hms.appointment.kafka.KafkaProducer;
import com.hms.appointment.mapper.DtoMapper;
import com.hms.appointment.repositry.AppointmentRepo;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService{


    private final DtoMapper dtoMapper;
    private final AppointmentRepo appointmentRepo;
    private final UserDetail userDetail;
    private final KafkaProducer producer;

    @Override
    @CircuitBreaker(name = "profileServiceCB", fallbackMethod = "profileFallback")
    @Retry(name = "profileServiceRetry")
    public void create(AppointmentRequestDto appointmentRequestDto) {
        log.info("i am working ..............................................................");

       UserDetailDto  user = userDetail.getUser(appointmentRequestDto.getPatientId());

       Optional<Appointment> appointment1 = appointmentRepo.findByEmail(user.getEmail());
       if(appointment1.isPresent()){
           throw  new RuntimeException("this appointment already exist");
       }
        Appointment appointment = dtoMapper.convertToEntity(appointmentRequestDto);
        appointmentRepo.save(appointment);
    }

    @Override
    public AppointmentReaponseDto changeStatus(AppointmentRequestDto appointmentRequestDto, Long id) {
        Appointment appointment = appointmentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        appointment.setStatus(appointmentRequestDto.getStatus());

        Appointment updated = appointmentRepo.save(appointment);

        producer.appointmentCreatedEvent(new AppointmentEvent(updated.getEmail(), updated.getAppointmentTime()));

        return dtoMapper.convertToResponse(updated);
    }

    public void profileFallback(AppointmentRequestDto dto, Throwable ex) {
        log.warn("⚠️ Fallback triggered due to {}", ex.toString());
        throw new RuntimeException("User service unavailable. Please try again later.");
    }

}
