package com.api.user.service;

import com.api.user.domain.entity.ChatRoomTb;
import com.api.user.domain.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ChatService {
    private final ChatRoomRepository chatRoomRepository;

    public List<ChatRoomTb> findAllRooms(){
       return chatRoomRepository.findAll();
    }

    public ChatRoomTb createChatRoom(String roomName){
        ChatRoomTb chatRoomTb = new ChatRoomTb() ;
        chatRoomTb.setRoomNm(roomName);
        return chatRoomRepository.save(chatRoomTb);
    }

    public ChatRoomTb findByRoomSq(long roomSq){
        return chatRoomRepository.findById(roomSq)
                .orElseThrow(()->new IllegalArgumentException("채팅방 고유번호를 확인하세요"));
    }
}
