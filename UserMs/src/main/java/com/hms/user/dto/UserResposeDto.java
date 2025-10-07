package com.hms.user.dto;

import com.hms.user.entity.Role;
import lombok.Builder;
import lombok.Data;


import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class UserResposeDto {

    private Long id;

    private String name;

    private String email;


    private Set<Role> roles = new HashSet<>();
}
