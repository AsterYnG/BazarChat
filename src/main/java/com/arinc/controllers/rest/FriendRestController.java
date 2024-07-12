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
        if (friendService.createFriendshipRequest(currentUser,friendshipReceiver).isPresent()){
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/friends")
    public ResponseEntity<List<FriendGetDto>> getAllFriendshipRequestsOnSubmit(@AuthenticationPrincipal UserDetails userDetails) {
        var userDto = UserDto.builder()
                .id(userService
                        .findUser(userDetails.getUsername())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN,"Not signed in")).getId())
                .build();
        return ResponseEntity.ok(friendService.getAllOnSubmitRequestsByFriendId(userDto));
    }

    @PostMapping("/friends/all")
    public ResponseEntity<List<FriendGetDto>> getAllFriendshipRequests(@RequestBody Integer ownerId) {
        return ResponseEntity.ok(friendService.getAllRequests(ownerId));
    }

    @GetMapping("/friends/self")
    public ResponseEntity<List<FriendGetDto>> getAllFriendshipRequestsOnSubmitByOwner(@AuthenticationPrincipal UserDetails userDetails) {
        var userDto = UserDto.builder()
                .id(userService
                        .findUser(userDetails.getUsername())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN,"Not signed in")).getId())
                .build();
        return ResponseEntity.ok(friendService.getAllOnSubmitRequestsByOwnerId(userDto));
    }

    @GetMapping("/friends/submitted")
    public ResponseEntity<List<FriendGetDto>> getAllSubmittedFriendships(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(friendService.getAllSubmittedFriendshipRequests(userDetails.getUsername()));
    }

    @DeleteMapping("/friends/delete")
    public ResponseEntity<String> deleteFriendshipRequest(@AuthenticationPrincipal UserDetails userDetails,@RequestBody Integer requestId) {
        if (friendService.deleteFriendshipRequest(userDetails.getUsername(),requestId)){
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/friends/delete/friend")
    public ResponseEntity<String> deleteFriend(@AuthenticationPrincipal UserDetails userDetails,@RequestBody Integer requestId) {
        if (friendService.deleteFriendshipRequest(userDetails.getUsername(),requestId)){
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping("/friends")
    public ResponseEntity<String> updateFriendshipRequest(@AuthenticationPrincipal UserDetails userDetails,@RequestBody Integer requestId) {
         if (friendService.updateFriendshipRequest(userDetails.getUsername(),requestId)){
             return ResponseEntity.status(HttpStatus.OK).build();
         }
         else return ResponseEntity.badRequest().body("Заявка в друзья отправлена не вам");
    }

}
