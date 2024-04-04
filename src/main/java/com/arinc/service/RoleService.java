package com.arinc.service;

import com.arinc.database.entity.Role;
import com.arinc.database.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Optional<Role> findByRoleName(String name){
        return roleRepository.findByRoleName(name);
    }
}
