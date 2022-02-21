package com.api.user.domain.repository;

import com.api.user.domain.entity.ChatRoomTb;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoomTb, Long> {
}