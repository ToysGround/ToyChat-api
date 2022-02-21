package com.api.user.domain.repository;

import com.api.user.domain.entity.ChatUserTb;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatUserRepository extends JpaRepository<ChatUserTb, Long> {
}