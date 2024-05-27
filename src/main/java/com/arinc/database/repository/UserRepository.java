package com.arinc.database.repository;

import com.arinc.database.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Transactional
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findByLoginAndPassword(String login, String password);

    List<User> findCustomersByOnlineIsTrue();

    Optional<User> findByLogin(String login);

    @Modifying(flushAutomatically = true)
    @Query("update User u set u.online = :online WHERE u.login = :login")
    void updateCustomerByLogin(String login, Boolean online);


}
