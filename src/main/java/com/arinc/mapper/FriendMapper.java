package com.arinc.mapper;

import com.arinc.database.entity.Friend;
import com.arinc.dto.FriendGetDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FriendMapper {
    private final UserMapper userMapper;

    public FriendGetDto mapFrom(Friend friend) {
        return FriendGetDto.builder()
                .id(friend.getId())
                .owner(userMapper.mapFrom(friend.getOwnerId()))
                .friend(userMapper.mapFrom(friend.getFriendId()))
                .status(friend.getStatusId().getStatus())
                .submittedDate(friend.getSubmittedDate())
                .build();
    }
}
