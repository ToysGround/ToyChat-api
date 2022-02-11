package com.api.user.common;

import com.api.user.controller.dto.ResponseDto;
import org.springframework.http.ResponseCookie;

import javax.servlet.http.Cookie;
import java.util.HashMap;

public class Com {
    public static ResponseDto createResponseDto(boolean success, String message, Object data){
        return ResponseDto.builder()
                .success(success)
                .message(message)
                .data(data)
                .build();
    }

    public static HashMap<String,Object> inputMap(boolean success, String message, Object data){
        HashMap<String, Object> map = new HashMap<>();
        map.put("success", success);
        map.put("message", message);
        map.put("data", data);
        return map;
    }

    public static ResponseCookie createCookie(String refreshKey){
        ResponseCookie cookie =ResponseCookie.from("refreshKey", refreshKey)
                                    .httpOnly(true)
                                    .secure(true)
                                    .path("/")
                                    .sameSite("Lax")
                .domain("localhost")
                                    .build();
        return cookie;
    }
}
