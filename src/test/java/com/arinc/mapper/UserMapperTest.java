package com.arinc.mapper;

import com.arinc.database.entity.Role;
import com.arinc.database.entity.User;
import com.arinc.dto.UserDto;
import com.arinc.dto.UserOAuthRegistrationDto;
import com.arinc.dto.UserRegistrationDto;
import com.arinc.service.RoleService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.testcontainers.shaded.org.checkerframework.checker.mustcall.qual.MustCall;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserMapperTest {
    @Mock
    private RoleService roleService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserMapper userMapper;

    private User user = User.builder()
            .id(1)
            .userPic("PIC")
            .login("login")
            .nickname("Nick")
            .name("NAME")
            .password("pass")
            .role(new Role(1,"ROLE","РОЛЬ"))
            .email("mail")
            .surname("surname")
            .online(true)
            .build();
    @DisplayName("Проверка на маппинг из User в UserDto")
    @Test
    void mapFrom() {
        var userDto = UserDto.builder()
                .userPic("/"+user.getUserPic())
                .login(user.getLogin())
                .nickname(user.getNickname())
                .name(user.getName())
                .password(user.getPassword())
                .role("РОЛЬ")
                .email(user.getEmail())
                .surname(user.getSurname())
                .online(true)
                .id(user.getId())
                .build();
        var result = userMapper.mapFrom(user);
        assertThat(result).isNotNull().isEqualTo(userDto);
    }
    @DisplayName("Проверка на маппинг из UserRegistrationDto в User")
    @Test
    void testMapFrom() {
        var userDto = UserRegistrationDto.builder()
                .userPic(new MockMultipartFile("name","name".getBytes()))
                .login(user.getLogin())
                .password(user.getPassword())
                .build();
        User userWithoutId = User.builder()
                .userPic(userDto.getUserPic().getOriginalFilename())
                .login("login")
                .password("pass")
                .role(new Role(1,"ROLE","РОЛЬ"))
                .build();
        when(roleService.findByRoleName("USER")).thenReturn(Optional.of(userWithoutId.getRole()));
        when(passwordEncoder.encode(userDto.getPassword())).thenReturn("pass");
        var result = userMapper.mapFrom(userDto);

        assertThat(result).isNotNull().isEqualTo(userWithoutId);
    }
    @DisplayName("Проверка на маппинг из UserOAuthRegistrationDto в User")
    @Test
    void testMapFrom1() {
        var userDto = UserOAuthRegistrationDto.builder()
                .userPic(user.getUserPic())
                .login(user.getLogin())
                .password(user.getPassword())
                .build();
        User userWithoutId = User.builder()
                .userPic(userDto.getUserPic())
                .login("login")
                .password("pass")
                .role(new Role(1,"ROLE","РОЛЬ"))
                .build();
        when(roleService.findByRoleName("USER")).thenReturn(Optional.of(userWithoutId.getRole()));
        when(passwordEncoder.encode(userDto.getPassword())).thenReturn("pass");
        var result = userMapper.mapFrom(userDto);

        assertThat(result).isNotNull().isEqualTo(userWithoutId);
    }
}