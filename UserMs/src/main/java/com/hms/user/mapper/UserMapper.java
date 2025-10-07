package com.hms.user.mapper;

import com.hms.user.dto.UserRequestDto;
import com.hms.user.entity.Role;
import com.hms.user.entity.Rolename;
import com.hms.user.entity.User;
import com.hms.user.repositry.RoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private  final PasswordEncoder passwordEncoder;
    private final RoleRepo roleRepo;

    public User convertToUser(UserRequestDto userRequestDto){

        Role defaultRole = roleRepo.findByName(Rolename.PATIENT)
                .orElseThrow(() -> new RuntimeException("Default role not found"));
        return User.builder()
                .name(userRequestDto.getName())
                .email(userRequestDto.getEmail())
                .password(passwordEncoder.encode(userRequestDto.getPassword()))
                .roles((new HashSet<>(Arrays.asList(defaultRole))))
                .build();
    }
}
