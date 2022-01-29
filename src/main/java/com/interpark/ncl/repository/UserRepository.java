package com.interpark.ncl.repository;

import com.interpark.ncl.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {

    @Query("SELECT u FROM Users u WHERE u.userNo = :userNo")
    Optional<Users> findUserByUserNo(String userNo);
}
