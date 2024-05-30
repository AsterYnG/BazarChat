package com.arinc.mapper;

import com.arinc.dto.UserDto;
import com.arinc.dto.UserOAuthRegistrationDto;
import com.arinc.dto.UserRegistrationDto;
import com.arinc.database.entity.User;
import com.arinc.service.RoleService;
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
                .userPic(user.getUserPic())
                .role(user.getRole().getRoleName())
                .build();
    }

    public User mapFrom(UserRegistrationDto userRegistrationDto) {



        //        var imageName = customerRegistrationDto.getUserPic().getOriginalFilename();
//        if (imageName.isEmpty()) {
//            imageName = "default_user_pic.jpg";
//        }
        var imagePath = userRegistrationDto.getUserPic().isEmpty() ? null : userRegistrationDto.getUserPic().getOriginalFilename();
        return User.builder()
                .login(userRegistrationDto.getLogin())
                .userPic(imagePath)
                .password(passwordEncoder.encode(userRegistrationDto.getPassword()))
                .role(roleService.findByRoleName("user").orElse(null))
                .build();
    }

    public User mapFrom(UserOAuthRegistrationDto userRegistrationDto) {
        return User.builder()
                .login(userRegistrationDto.getLogin())
                .userPic(userRegistrationDto.getUserPic())
                .password(passwordEncoder.encode(userRegistrationDto.getPassword()))
                .role(roleService.findByRoleName("USER").orElse(null))
                .build();
    }
}
