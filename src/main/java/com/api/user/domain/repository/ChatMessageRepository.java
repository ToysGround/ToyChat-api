package com.api.user.domain.repository;

import com.api.user.domain.entity.ChatMessageTb;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessageTb, Long> {
}