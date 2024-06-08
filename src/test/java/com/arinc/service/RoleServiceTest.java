package com.arinc.service;

import com.arinc.database.entity.Role;
import com.arinc.database.repository.RoleRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class RoleServiceTest {
    @Mock
    private RoleRepository roleRepository;
    @InjectMocks
    private RoleService roleService;

    private final String roleName = "USER";

    @Test
    void findByRoleName() {
        var role =Role.builder()
                .roleName(roleName)
                .roleNameRu("Лох")
                .id(1)
                .build();
            Mockito.when(roleRepository.findByRoleName(roleName)).thenReturn(Optional.of(role));

            var actualResult = roleService.findByRoleName(roleName);

            actualResult.ifPresent(result -> {
                Assertions.assertThat(result).isEqualTo(role);
            });
    }
}