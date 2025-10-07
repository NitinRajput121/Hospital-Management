package com.hms.user.feign;

import com.hms.user.dto.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ProfileMs")
public interface PatientClient {

         @PostMapping("/profile/patient/create")
    void createPatientProfile(@RequestBody Patient patient);
}
