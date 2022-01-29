package com.api.user.controller;

import com.api.user.domain.entity.UserTb;
import com.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@RequestBody UserTb user){
        HashMap<String, String> map = new HashMap<>();
        try {
            String signUpId = userService.insert(user);
            map.put("success","true");
            map.put("message","가입 성공");
            map.put("data",signUpId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(map, HttpStatus.OK); //200
    }

    @GetMapping("/find")
    public ResponseEntity<?> findAll(){
        System.out.println("FIND ALL");
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK); //200
    }

    @GetMapping("/find/{sq}")
    public ResponseEntity<?> findById(@PathVariable long sq){
        return new ResponseEntity<>(userService.findOne(sq), HttpStatus.OK); //200
    }

    @PutMapping("/modify/{sq}")
    public ResponseEntity<?> modify(@PathVariable long sq,@RequestBody UserTb user){
        return new ResponseEntity<>(userService.modify(sq,user), HttpStatus.OK); //200
    }

    @DeleteMapping("/delete/{sq}")
    public ResponseEntity<?> delete(@PathVariable long sq){
        return new ResponseEntity<>(userService.delete(sq), HttpStatus.OK); //200
    }

    @GetMapping("/findId")
    public ResponseEntity<?> findByUserId(@RequestBody Map data){
        HashMap<String, String> map = new HashMap<>();

        if(userService.findByUserId(data.get("userId").toString())){
            map.put("success", "true");
            map.put("message","사용 가능한 ID입니다.");
            map.put("data",null);
        }
        else {
            map.put("success", "false");
            map.put("message","사용중인 ID입니다.");
            map.put("data",null);
        }

        return new ResponseEntity<>(map, HttpStatus.OK); //200
    }

}
