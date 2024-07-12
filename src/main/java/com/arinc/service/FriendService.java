package com.arinc.service;

import com.arinc.database.entity.Friend;
import com.arinc.database.repository.FriendRepository;
import com.arinc.database.repository.FriendshipStatusRepository;
import com.arinc.database.repository.UserRepository;
import com.arinc.dto.FriendGetDto;
import com.arinc.dto.UserDto;
import com.arinc.mapper.FriendMapper;
import com.arinc.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendRepository friendRepository;
    private final FriendshipStatusRepository friendshipStatusRepository;
    private final UserRepository userRepository;
    private final FriendMapper friendMapper;
    private final SimpMessagingTemplate messagingTemplate;

    public void createFriendshipRequest(UserDto owner, UserDto friend) {
        var friendRequest = Friend.builder()
                .ownerId(userRepository.findById(owner.getId())
                        .orElseThrow(() -> new RuntimeException("No such user in Database: %s".formatted(owner.getId())))
                )
                .friendId(userRepository.findById(friend.getId())
                        .orElseThrow(() -> new RuntimeException("No such user in Database: %s".formatted(friend.getId())))
                )
                .statusId(friendshipStatusRepository.findById(FriendShipStatus.ON_SUBMIT.value)
                        .orElseThrow(() -> new NoSuchElementException("No such status in DB for this one")))
                .build();
        friendRepository.save(friendRequest);
        messagingTemplate.convertAndSend("/topic/friendship/" + friend.getId(), friendMapper.mapFrom(friendRequest));
    }

    public List<FriendGetDto> getAllOnSubmitRequests(UserDto friendId) {
        return friendRepository.findAllByFriendId(userRepository.findById(friendId.getId())
                        .orElseThrow(() -> new NoSuchElementException("No such user in DB"))).stream()
                .map(friendMapper::mapFrom)
                .toList();
    }


    //TODO: Сделать еще функционал для сервиса!

    @RequiredArgsConstructor
    private enum FriendShipStatus {
        ON_SUBMIT(2),
        SUBMITTED(3);

        private final int value;
    }
}
