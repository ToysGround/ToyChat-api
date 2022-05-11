package com.api.user.repository;

import com.api.user.entity.ChatMessageTb;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessageTb, Long> {
}