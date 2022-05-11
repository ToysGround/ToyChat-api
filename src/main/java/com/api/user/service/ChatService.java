package com.api.user.service;

import com.api.user.controller.dto.ChatDto;
import com.api.user.entity.ChatMessageTb;
import com.api.user.entity.ChatRoomTb;
import com.api.user.entity.ChatUserTb;
import com.api.user.repository.ChatMessageRepository;
import com.api.user.repository.ChatRoomRepository;
import com.api.user.repository.ChatUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ChatService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatUserRepository chatUserRepository;

    public List<ChatRoomTb> findAllRooms(){
       return chatRoomRepository.findAll();
    }

    public ChatRoomTb createChatRoom(String roomName, long chatUserSq, long roomSq, long userSQ){
        ChatRoomTb chatRoomTb = new ChatRoomTb();
        ChatUserTb chatUserTb = new ChatUserTb();

        chatRoomTb.setRoomNm(roomName);

        chatUserTb.setChatUserSq(chatUserSq);
        chatUserTb.setRoomSq(roomSq);
        chatUserTb.setUserSq(userSQ);
        chatUserRepository.save(chatUserTb);

        return chatRoomRepository.save(chatRoomTb);
    }

    public ChatRoomTb findByRoomSq(long roomSq){
        return chatRoomRepository.findById(roomSq)
                .orElseThrow(()->new IllegalArgumentException("채팅방 고유번호를 확인하세요"));
    }

    public ChatMessageTb saveChatDto(ChatDto ChatDto){
        ChatMessageTb chatMessageTb = new ChatMessageTb();
        chatMessageTb.setRoomSq(ChatDto.getRoomSq());
        chatMessageTb.setUserSq(ChatDto.getUserSq());
        chatMessageTb.setChatMsg(ChatDto.getChatMsg());

        return chatMessageRepository.save(chatMessageTb);
    }
}
