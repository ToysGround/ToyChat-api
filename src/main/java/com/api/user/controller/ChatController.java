package com.api.user.controller;

import com.api.user.controller.dto.ChatDto;
import com.api.user.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
@Log4j2
public class ChatController {

    private final SimpMessagingTemplate template ;
    private final ChatService chatService;

    //TEST
    @MessageMapping(value = "/enter")
    public void enter(String message){
        log.info("message :: " + message);
        template.convertAndSend("/sub/enter", message);
    }

    @MessageMapping(value = "/enter/{roomSq}")
    public void enter(@PathVariable long roomSq,@RequestBody ChatDto chatDto){
        log.info("message :: " + chatDto.getChatMsg());
        chatService.saveChatDto(chatDto);
        template.convertAndSend("/sub/enter/" + roomSq,
                                chatDto.getUserNm() + " : " + chatDto.getChatMsg());
    }


/*
    @MessageMapping(value = "/message")
    @SendTo("/sub/message")
    public String message(String message) throws Exception{
        log.info("message :: " + message);
        return message;
    }
    */
    /*
    @MessageMapping(value = "/chat/enter")
    public void enter(ChatMessageTb message){
        message.setChatMsg("dsa");
        template.convertAndSend("/sub/chat/room" + message.getRoomSq(),message);

    }

    @MessageMapping(value = "/chat/message")
    public void message(ChatMessageTb message){
        template.convertAndSend("/sub/chat/room/1" , message);
    }
    */

}
