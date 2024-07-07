package com.arinc.controllers.rest;

import com.arinc.service.OnlineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/online")
@RequiredArgsConstructor
public class OnlineRestController {

    private final OnlineService onlineService;

    @PostMapping("/{userId}")
    public void turnOnline(@PathVariable Integer userId,@RequestBody Boolean online){
        onlineService.setOnline(userId, online);
    }

}
