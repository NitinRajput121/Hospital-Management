package com.hms.appointment.feign;

import com.hms.appointment.dto.UserDetailDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "UserMs")
public interface UserDetail {

    @GetMapping("/user/get/{id}")
    UserDetailDto getUser(@PathVariable("id") Long id);


}
