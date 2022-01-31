package com.api.user.common;

import java.util.HashMap;

public class Com {
    public static HashMap<String,Object> inputMap(boolean success, String message, String data){
        HashMap<String, Object> map = new HashMap<>();
        map.put("success", success);
        map.put("message", message);
        map.put("data", data);
        return map;
    }
}
