package com.arinc.database.repository;

import com.arinc.database.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface MessageRepository extends JpaRepository<Message,Integer> {
    List<Message> findTop10ByOrderByIdDesc();
}


