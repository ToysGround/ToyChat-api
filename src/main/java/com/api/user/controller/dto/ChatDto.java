package com.api.user.controller.dto;

import lombok.Data;

@Data
public class ChatDto {

    private String roomId;
    private String writer;
    private String message;
}
