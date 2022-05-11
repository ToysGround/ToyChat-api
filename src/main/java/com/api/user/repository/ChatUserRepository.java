package com.api.user.repository;

import com.api.user.entity.ChatUserTb;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatUserRepository extends JpaRepository<ChatUserTb, Long> {
}