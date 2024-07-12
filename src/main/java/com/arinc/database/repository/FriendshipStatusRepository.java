package com.arinc.database.repository;

import com.arinc.database.entity.FriendStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface FriendshipStatusRepository extends JpaRepository<FriendStatus, Integer> {
}
