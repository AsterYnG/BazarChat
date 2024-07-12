package com.arinc.database.repository;

import com.arinc.database.entity.Friend;
import com.arinc.database.entity.FriendStatus;
import com.arinc.database.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface FriendRepository extends JpaRepository<Friend, Integer> {

    List<Friend> findAllByFriendIdAndStatusIdIs(User friendId, FriendStatus status);

    List<Friend> findAllByOwnerIdAndStatusIdIs(User ownerId, FriendStatus status);

    Optional<Friend> findByOwnerIdAndFriendId(User owner, User friendId);

    List<Friend> findAllByFriendIdAndStatusIdOrOwnerIdAndStatusId(User friendId, FriendStatus statusId, User ownerId, FriendStatus statusId2);

    List<Friend> findAllByFriendIdOrOwnerId(User friendId, User ownerId);
}
