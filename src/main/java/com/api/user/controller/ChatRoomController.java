package com.api.user.controller;

import com.api.user.common.Com;
import com.api.user.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@Log4j2
//@RequestMapping("/chat")
public class ChatRoomController {
    private final ChatService chatService;

    @GetMapping("/rooms")
    public ResponseEntity<?> findAllRooms(){
        log.info("# ALL Chat Rooms");
        return new ResponseEntity<>(
                Com.inputMap(true,"채팅방 목록 조회 완료.", chatService.findAllRooms()),
                HttpStatus.OK); //200
    }

    @PostMapping("/room")
    public ResponseEntity<?> createRoom(@RequestBody Map map, RedirectAttributes rttr){
        log.info("# Create Chat Room , Data :: " + map.toString());
        rttr.addAttribute("roomName", chatService.createChatRoom(String.valueOf(map.get("roomNm"))));
        return  new ResponseEntity<>(
                Com.inputMap(true,"채팅방 생성 완료", chatService.findAllRooms()),
                HttpStatus.OK); //200
    }

    @GetMapping("/room")
    public ResponseEntity<?> getRoom(@RequestParam long roomId){
        log.info("# get Chat Room, roomID : " + roomId);
        return new ResponseEntity<>(
                Com.inputMap(true,"채팅방 조회 완료", chatService.findByRoomSq(roomId)),
                HttpStatus.OK); //200
    }

}
