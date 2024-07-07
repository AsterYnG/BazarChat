package com.arinc.service;

import com.arinc.database.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OnlineService {

    private final UserService userService;

    public void setOnline(Integer userId, boolean online) {
        userService.setOnline(userId, online);
    }
}
