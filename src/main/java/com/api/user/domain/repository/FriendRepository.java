package com.api.user.domain.repository;

import com.api.user.domain.entity.FriendTb;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<FriendTb, Long> {
}