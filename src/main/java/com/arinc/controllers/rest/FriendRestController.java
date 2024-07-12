package com.arinc.controllers.rest;

import com.arinc.dto.FriendGetDto;
import com.arinc.dto.UserDto;
import com.arinc.service.FriendService;
import com.arinc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@PreAuthorize("isAuthenticated()")
public class FriendRestController {
    private final FriendService friendService;
    private final UserService userService;

    @PostMapping("/friend")
    public ResponseEntity<String> createFriendshipRequest(@AuthenticationPrincipal UserDetails userDetails,@RequestBody Integer friendId) {
        var currentUser = userService.findUser(userDetails.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN,"You're not logged in"));
        var friendshipReceiver = userService.findUserById(friendId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Friendship receiver not found"));
        friendService.createFriendshipRequest(currentUser,friendshipReceiver);
        return ResponseEntity.ok("Friendship request created");
    }

    @GetMapping("/friends")
    public ResponseEntity<List<FriendGetDto>> getAllFriendshipRequestsOnSubmit(@AuthenticationPrincipal UserDetails userDetails) {
        var userDto = UserDto.builder()
                .id(userService
                        .findUser(userDetails.getUsername())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN,"Not signed in")).getId())
                .build();
        return ResponseEntity.ok(friendService.getAllOnSubmitRequests(userDto));
    }


}
