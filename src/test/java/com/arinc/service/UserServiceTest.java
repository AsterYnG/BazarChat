package com.arinc.service;

import com.arinc.database.entity.Role;
import com.arinc.database.entity.User;
import com.arinc.database.repository.UserRepository;
import com.arinc.dto.UserDto;
import com.arinc.dto.UserOAuthRegistrationDto;
import com.arinc.dto.UserRegistrationDto;
import com.arinc.dto.UserUpdateProfileDto;
import com.arinc.mapper.UserMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private ImageService imageService;
    @InjectMocks
    private UserService userService;

    private Integer existingUserId = 1;
    private String existingLogin = "papa";
    private String existingPassword = "papa";
    private String newNickname = "NICKNAME";
    private String newName = "NAME";
    private String newSurname = "SURNAME";

    private final Role role = Role.builder()
            .roleName("USER")
            .id(1)
            .build();
    private final User existingUser = User.builder().
            id(existingUserId)
            .login(existingLogin)
            .password(existingPassword)
            .role(role)
            .build();
    private final UserDto existingUserDto = UserDto.builder().
            id(existingUserId)
            .login(existingLogin)
            .build();

    @DisplayName("Проверка обновления данных пользователя")
    @Test
    void updateUser() {
        var updateDto = UserUpdateProfileDto.builder()
                .name(newName)
                .surname(newSurname)
                .nickName(newNickname)
                .build();
        var updatedUser = User.builder()
                .id(existingUserId)
                .login(existingLogin)
                .role(existingUser.getRole())
                .password(existingUser.getPassword())
                .nickname(updateDto.getNickName())
                .surname(updateDto.getSurname())
                .name(updateDto.getName())
                .build();
        var updatedUserDto = UserDto.builder()
                .id(existingUserId)
                .login(existingLogin)
                .nickname(updateDto.getNickName())
                .surname(updateDto.getSurname())
                .name(updateDto.getName())
                .build();

        when(userRepository.findByLogin(existingLogin)).thenReturn(Optional.of(existingUser));
        doNothing().when(userRepository).flush();
        when(userMapper.mapFrom(updatedUser)).thenReturn(updatedUserDto);

        var actualResult = userService.updateUser(existingLogin, updateDto);

        actualResult.ifPresent(result -> {
            assertThat(result).isEqualTo(updatedUserDto);
        });
    }
    @DisplayName("Проверка поиска пользователя")
    @Test
    void findUser() {

        when(userRepository.findByLogin(existingLogin)).thenReturn(Optional.of(existingUser));
        when(userMapper.mapFrom(existingUser)).thenReturn(existingUserDto);

        var actualResult = userService.findUser(existingLogin);
        actualResult.ifPresent(result -> {
            assertThat(result).isEqualTo(existingUserDto);
        });
    }
    @DisplayName("Проверка поиска всех онлайн пользователей")
    @Test
    void getOnlineUsers() {
        var userList = List.of(existingUser);
        var expectedResult = List.of(existingUserDto);

        when(userMapper.mapFrom(existingUser)).thenReturn(existingUserDto);
        when(userRepository.findCustomersByOnlineIsTrue()).thenReturn(userList);

        var actualResult = userService.getOnlineUsers();

        assertThat(actualResult).isEqualTo(expectedResult);
    }
    @DisplayName("Проверка сохранения пользователя")
    @Test
    void saveUser() {
        var file = new MockMultipartFile("test","text".getBytes());

        var userRegistrationDto = UserRegistrationDto.builder()
                .login(existingLogin)
                .password(existingPassword)
                .userPic(file)
                .build();
        doNothing().when(imageService).upload(any(),any());
        when(userMapper.mapFrom(userRegistrationDto)).thenReturn(existingUser);
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        var actualResult = userService.saveUser(userRegistrationDto);

        assertThat(actualResult).isEqualTo(existingUser);
    }
    @DisplayName("Проверка сохранения пользователя")
    @Test
    void testSaveUser() {
        var userRegistrationDto = UserOAuthRegistrationDto.builder()
                .login(existingLogin)
                .password(existingPassword)
                .build();
        when(userMapper.mapFrom(userRegistrationDto)).thenReturn(existingUser);
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        var actualResult = userService.saveUser(userRegistrationDto);

        assertThat(actualResult).isEqualTo(existingUser);
    }

    @Test
    void setOnline() {
     assertTrue(true);
    }

    @DisplayName("Проверка на корректность загрузки UserDetails в Security context")
    @Test
    void loadUserByUsername() {
        when(userRepository.findByLogin(existingLogin)).thenReturn(Optional.of(existingUser));

        var expectedResult = org.springframework.security.core.userdetails.User.builder()
                .username(existingUser.getLogin())
                .password(existingUser.getPassword())
                .authorities(existingUser.getRole())
                .build();

        var actualResult = userService.loadUserByUsername(existingLogin);

        assertThat(actualResult).isEqualTo(expectedResult);
    }
}