package com.hms.user.dto;

import com.hms.user.entity.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {

    @NotBlank(message = "name can not be blank")
    private String name;
    @Email(message = "email should be in good format")
    @NotBlank(message = "email can not be blank")
    @Column(unique = true)
    private String email;
    @NotBlank(message = "password can not be blank")
    private String password;

    private Set<Role> roles = new HashSet<>();
}
