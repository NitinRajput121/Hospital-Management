package com.hms.profile.repositries;

import com.hms.profile.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepo extends JpaRepository<Patient,Long> {
    boolean existsByEmail(String email);
}
