package com.api.user.controller;

import com.api.user.controller.dto.ChatDto;
import com.api.user.domain.entity.ChatMessageTb;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Log4j2
public class ChatController {

    private final SimpMessagingTemplate template ;
    @MessageMapping(value = "/chat/enter")
    public void enter(ChatDto message){
        log.info("message :: " + message);
        template.convertAndSend("/sub/chat/room/1",message);
       // return message + "dsadsads";
    }

    @MessageMapping(value = "/chat/message")
    @SendTo("/sub/message")
    public String message(String message){
        log.info("message :: " + message);
        return message;
    }
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
