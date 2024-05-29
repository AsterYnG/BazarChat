package com.arinc.service;

import com.arinc.database.entity.User;
import com.arinc.database.repository.UserRepository;
import com.arinc.dto.UserDto;
import com.arinc.dto.UserOAuthRegistrationDto;
import com.arinc.dto.UserRegistrationDto;
import com.arinc.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@PreAuthorize("permitAll()")
@RequiredArgsConstructor
public class UserService implements UserDetailsService {


    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ImageService imageService;


    public Optional<UserDto> findUser(String login, String password) {
        var customerEntity = userRepository.findByLoginAndPassword(login, password);
        return customerEntity.map(userMapper::mapFrom);
    }

    public Optional<UserDto> findUser(String username){
        return userRepository.findByLogin(username).map(userMapper::mapFrom);
    }

    public List<UserDto> getOnlineUsers() {
        var onlineUsers = userRepository.findCustomersByOnlineIsTrue();
        return onlineUsers.stream()
                .map(userMapper::mapFrom)
                .collect(Collectors.toList());
    }



    public User saveUser(UserRegistrationDto customerDto){
        return Optional.of(customerDto)
                .map(dto -> {
                    try {
                        imageService.upload(dto.getUserPic().getOriginalFilename(),dto.getUserPic().getInputStream());
                    } catch (IOException e) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST); //TODO: Доделать с обработкой когда картинку не грузят
                    }
                    return dto;
                })
                .map(userMapper::mapFrom)
                .map(userRepository::save)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        //        var customer = customerMapper.mapFrom(customerDto);
//        imageService.upload(customer.getUserPic(), customerDto.getUserPic().getInputStream());
//        customerRepository.save(customer);
    }

    public User saveUser(UserOAuthRegistrationDto customerDto){
        return Optional.of(customerDto)
                .map(userMapper::mapFrom)
                .map(userRepository::save)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
    }

    public void setOnline(String userName, boolean isOnline){
        userRepository.updateCustomerByLogin(userName,isOnline);
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByLogin(username)
                .map(user -> org.springframework.security.core.userdetails.User.builder()
                        .username(user.getLogin())
                        .password(user.getPassword())
                        .authorities(user.getRole())
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("Can't find user: "+ username));
    }

}
