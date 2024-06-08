package com.arinc.integration.database.repository;

import com.arinc.database.entity.Role;
import com.arinc.database.entity.User;
import com.arinc.database.repository.UserRepository;
import com.arinc.integration.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
class UserRepositoryTest extends IntegrationTestBase {

    private final UserRepository userRepository;

    private final User expected = User.builder()
            .id(1)
            .email("khud@mail.ru")
            .login("test")
            .name("test")
            .password("test")
            .surname("test")
            .online(true)
            .nickname("test")
            .role(Role.builder()
                    .id(1)
                    .roleName("USER")
                    .roleNameRu("Лох")
                    .build())
            .build();

    @Test
    void findByLoginAndPassword() {
        Optional<User> actual = userRepository.findByLoginAndPassword("test", "test");
        assertThat(actual).isPresent();

        actual.ifPresent(user -> assertEquals(user, expected));
    }

    @Test
    void findCustomersByOnlineIsTrue() {
        List<User> actual = userRepository.findCustomersByOnlineIsTrue();

        org.assertj.core.api.Assertions.assertThat(actual).hasSize(1);
    }

    @Test
    void findByLogin() {
        Optional<User> actual = userRepository.findByLogin("test");

        assertThat(actual).isPresent().isEqualTo(Optional.of(expected));
    }

    @Test
    void findByEmail() {
        Optional<User> actual = userRepository.findByEmail("khud@mail.ru");

        assertThat(actual).isPresent().isEqualTo(Optional.of(expected));
    }

}