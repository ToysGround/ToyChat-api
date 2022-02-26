package com.api.user.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class UserDto {
    private long userSq;
    private String userId;
    private String userNm;
    private String userMsg;
    private String userImage;
}
