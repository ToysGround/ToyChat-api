package com.api.user.repository;

import com.api.user.entity.ChatRoomTb;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoomTb, Long> {
}