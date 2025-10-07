package com.hms.appointment.repositry;

import com.hms.appointment.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment,Long> {

    Optional<Appointment> findByEmail(String email);
}
