package com.arinc.integration.database.repository;

import com.arinc.database.entity.Role;
import com.arinc.database.repository.RoleRepository;
import com.arinc.integration.IntegrationTestBase;
import com.arinc.integration.annotation.IntegrationTest;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
class RoleRepositoryTest extends IntegrationTestBase {

    private final RoleRepository roleRepository;
    @Test
    void findByRoleName() {
        Optional<Role> role = roleRepository.findByRoleName("USER");

        assertTrue(role.isPresent());
        assertEquals("USER", role.get().getRoleName());
    }
}