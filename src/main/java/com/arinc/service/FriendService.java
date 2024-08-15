package com.arinc.service;

import com.arinc.database.entity.Friend;
import com.arinc.database.repository.FriendRepository;
import com.arinc.database.repository.FriendshipStatusRepository;
import com.arinc.database.repository.UserRepository;
import com.arinc.dto.FriendGetDto;
import com.arinc.dto.UserDto;
import com.arinc.mapper.FriendMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendRepository friendRepository;
    private final FriendshipStatusRepository friendshipStatusRepository;
    private final UserRepository userRepository;
    private final FriendMapper friendMapper;
    private final SimpMessagingTemplate messagingTemplate;

    public Optional<Friend> createFriendshipRequest(UserDto owner, UserDto friend) {
        var friendRequest = Friend.builder()
                .ownerId(userRepository.findById(owner.getId())
                        .orElseThrow(() -> new NoSuchElementException("No such user in Database: %s".formatted(owner.getId())))
                )
                .friendId(userRepository.findById(friend.getId())
                        .orElseThrow(() -> new NoSuchElementException("No such user in Database: %s".formatted(friend.getId())))
                )
                .statusId(friendshipStatusRepository.findById(FriendShipStatus.ON_SUBMIT.value)
                        .orElseThrow(() -> new NoSuchElementException("No such status in DB for this one")))
                .build();
        if (!isMirrorRequestExists(friendRequest)) {
            friendRepository.save(friendRequest);
            messagingTemplate.convertAndSend("/topic/friendship/requests", friendMapper.mapFrom(friendRequest));
            return Optional.of(friendRequest);
        } else {
            return Optional.empty();
        }
    }

    public boolean updateFriendshipRequest(String ownerLogin, Integer friendshipRequestId) {
        AtomicBoolean success = new AtomicBoolean(false);
        friendRepository.findById(friendshipRequestId)
                .ifPresent(request -> {
                    if (request.getFriendId().getLogin().equals(ownerLogin)) {
                        request.setStatusId(friendshipStatusRepository.findById(FriendShipStatus.SUBMITTED.value)
                                .orElseThrow(() -> new NoSuchElementException("No such status in DB for this one")));
                        request.setSubmittedDate(LocalDateTime.now());
                        friendRepository.saveAndFlush(request);
                        success.set(true);
                        messagingTemplate.convertAndSend("/topic/friendship/friends", friendMapper.mapFrom(request));
                    }
                });
        return success.get();
    }

    public List<FriendGetDto> getAllOnSubmitRequestsByFriendId(UserDto friendId) {
        return friendRepository.findAllByFriendIdAndStatusIdIs(userRepository.findById(friendId.getId())
                                .orElseThrow(() -> new NoSuchElementException("No such user in DB")),
                        friendshipStatusRepository.findById(FriendShipStatus.ON_SUBMIT.value)
                                .orElseThrow(() -> new NoSuchElementException("No such status in DB."))).stream()
                .map(friendMapper::mapFrom)
                .toList();
    }

    public List<FriendGetDto> getAllOnSubmitRequestsByOwnerId(UserDto ownerId) {
        return friendRepository.findAllByOwnerIdAndStatusIdIs(userRepository.findById(ownerId.getId())
                        .orElseThrow(() -> new NoSuchElementException("No such user in DB")),
                        friendshipStatusRepository.findById(FriendShipStatus.ON_SUBMIT.value)
                                .orElseThrow(() -> new NoSuchElementException("No such status in DB."))).stream()
                .map(friendMapper::mapFrom)
                .toList();
    }

    public List<FriendGetDto> getAllSubmittedFriendshipRequests(String ownerLogin) {
        var owner = userRepository.findByLogin(ownerLogin)
                .orElseThrow(() -> new NoSuchElementException("No such user in DB"));
        var status = friendshipStatusRepository.findById(FriendShipStatus.SUBMITTED.value)
                .orElseThrow(() -> new NoSuchElementException("No such status in DB."));


        return friendRepository.findAllByFriendIdAndStatusIdOrOwnerIdAndStatusId(owner,status, owner,status)
                .stream()
                .map(friendMapper::mapFrom)
                .toList();
    }

    public List<FriendGetDto> getAllRequests(Integer ownerId){
        var owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new NoSuchElementException("No such user in DB"));
        return friendRepository.findAllByFriendIdOrOwnerId(owner,owner)
                .stream()
                .map(friendMapper::mapFrom)
                .toList();
    }

    public boolean deleteFriendshipRequest(String ownerLogin, Integer requestId) {
        var mayBeRequest = friendRepository.findById(requestId);
        var user = userRepository.findByLogin(ownerLogin).orElseThrow(() -> new NoSuchElementException("No such user in DB"));
        if (mayBeRequest.isPresent()) {
            var request = mayBeRequest.get();
            if (request.getFriendId().equals(user) || request.getOwnerId().equals(user)) {
                friendRepository.deleteById(requestId);
                return true;
            }
            else return false;
        }
        else throw new NoSuchElementException("No such request in DB.");
    }

    private boolean isMirrorRequestExists(Friend friendShipRequest) {
        return friendRepository.findByOwnerIdAndFriendId(friendShipRequest.getFriendId(), friendShipRequest.getOwnerId())
                .isPresent();
    }


    //TODO: Сделать еще функционал для сервиса!

    @RequiredArgsConstructor
    private enum FriendShipStatus {
        ON_SUBMIT(2),
        SUBMITTED(3);

        private final int value;
    }
}
