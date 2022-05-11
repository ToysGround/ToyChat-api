package com.api.user.controller.dto;

import lombok.Data;

@Data
public class ChatDto {

    private long roomSq;
    private long userSq;
    private String userNm;
    private String chatMsg;
}
