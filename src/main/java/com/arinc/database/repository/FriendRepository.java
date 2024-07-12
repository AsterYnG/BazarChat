package com.arinc.database.repository;

import com.arinc.database.entity.Friend;
import com.arinc.database.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface FriendRepository extends JpaRepository<Friend, Integer> {

    List<Friend> findAllByFriendId(User friendId);
}
