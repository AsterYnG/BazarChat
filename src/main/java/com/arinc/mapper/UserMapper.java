package com.arinc.mapper;

import com.arinc.database.entity.User;
import com.arinc.dto.UserDto;
import com.arinc.dto.UserOAuthRegistrationDto;
import com.arinc.dto.UserRegistrationDto;
import com.arinc.service.ImageService;
import com.arinc.service.RoleService;
import com.arinc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public UserDto mapFrom(User user) {
        return UserDto.builder()
                .id(user.getId())
                .login(user.getLogin())
                .password(user.getPassword())
                .userPic('/' + user.getUserPic())
                .role(user.getRole().getRoleNameRu())
                .surname(user.getSurname())
                .email(user.getEmail())
                .name(user.getName())
                .online(user.isOnline())
                .nickname(user.getNickname())
                .build();
    }

    public User mapFrom(UserRegistrationDto userRegistrationDto) {
        var imagePath = userRegistrationDto.getUserPic().isEmpty() ? "default.png" : userRegistrationDto.getUserPic().getOriginalFilename();
        return User.builder()
                .login(userRegistrationDto.getLogin())
                .userPic(imagePath)
                .password(passwordEncoder.encode(userRegistrationDto.getPassword()))
                .role(roleService.findByRoleName("USER").orElse(null))
                .build();
    }

    public User mapFrom(UserOAuthRegistrationDto userRegistrationDto) {
        return User.builder()
                .email(userRegistrationDto.getEmail())
                .login(userRegistrationDto.getLogin())
                .userPic(userRegistrationDto.getUserPic())
                .password(passwordEncoder.encode(userRegistrationDto.getPassword()))
                .role(roleService.findByRoleName("USER").orElse(null))
                .build();
    }


}
