package com.api.user.domain.repository;

import com.api.user.domain.entity.FriendTb;
import com.api.user.domain.entity.UserTb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FriendRepository extends JpaRepository<FriendTb, Long> {
    @Query("select f.toUserSq from FriendTb f where f.userSq = :target")
    Optional<List<Long>> findByUserSqList(@Param("target") long userId);
}