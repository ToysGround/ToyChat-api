package com.api.user.common;

import javax.servlet.http.Cookie;
import java.util.HashMap;

public class Com {
    public static HashMap<String,Object> inputMap(boolean success, String message, Object data){
        HashMap<String, Object> map = new HashMap<>();
        map.put("success", success);
        map.put("message", message);
        map.put("data", data);
        return map;
    }

    public static Cookie createCookie(String refreshKey){
        Cookie cookie = new Cookie("refreshKey", refreshKey);
        cookie.setPath("/");
        return cookie;
    }
}
