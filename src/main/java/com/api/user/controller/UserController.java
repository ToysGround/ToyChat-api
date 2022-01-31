package com.api.user.controller;

import com.api.user.common.Com;
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
@CrossOrigin
public class UserController {

    private final UserService userService;
    public Com com;

    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@RequestBody UserTb user){
        HashMap<String, Object> map = new HashMap<>();
        try {
            String signUpId = userService.insert(user);
            map.putAll(com.inputMap(true,"가입 성공",signUpId));
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(map, HttpStatus.OK); //200
    }

    @PostMapping("/signIn")
    public ResponseEntity<?> signIn(@RequestBody UserTb user){
        HashMap<String, Object> map = new HashMap<>();
        try {
            String signInId = user.getUserId();
            String signInPwd = user.getUserPwd();
            UserTb userEntity = userService.findByUserIdReturnUser(signInId);
            if(userEntity == null) {
                map.putAll(com.inputMap(false,"ID를 다시입력해주세요.",signInId));
                return new ResponseEntity<>(map, HttpStatus.OK); //200
            }else if(!userEntity.getUserPwd().equalsIgnoreCase(signInPwd)){
                map.putAll(com.inputMap(false,"PWD를 다시입력해주세요.",signInPwd));
                return new ResponseEntity<>(map, HttpStatus.OK); //200
            }else {
                map.putAll(com.inputMap(true,"로그인 성공",null));
            }
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
        HashMap<String, Object> map = new HashMap<>();
        try {
            UserTb userEntity = userService.findOne(sq);
            String userId = userEntity.getUserId();
            String signUpId = userService.delete(sq);
            map.putAll(com.inputMap(true,"삭제 완료",userId));
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(map, HttpStatus.OK); //200
    }

    @PostMapping("/findId")
    public ResponseEntity<?> findByUserId(@RequestBody Map data){
        HashMap<String, Object> map = new HashMap<>();

        if(userService.findByUserId(data.get("userId").toString())){
            map.putAll(com.inputMap(true,"사용 가능한 ID입니다.",null));
        }
        else {
            map.putAll(com.inputMap(false,"사용중인 ID입니다.",null));
        }

        return new ResponseEntity<>(map, HttpStatus.OK); //200
    }

}
