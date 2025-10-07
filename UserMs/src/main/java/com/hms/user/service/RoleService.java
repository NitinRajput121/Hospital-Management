package com.hms.user.service;

import com.hms.user.entity.Role;
import com.hms.user.repositry.RoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepo roleRepo;

    public void create(Role role){
        roleRepo.save(role);
    }
}
