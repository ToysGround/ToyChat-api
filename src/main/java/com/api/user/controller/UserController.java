package com.api.user.controller;

import com.api.user.common.Com;
import com.api.user.controller.dto.ResponseDto;
import com.api.user.controller.dto.TokenDto;
import com.api.user.domain.entity.UserTb;
import com.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.springframework.http.*;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
//@CrossOrigin(origins = {"http://localhost:8081", "http://localhost:3000"}, allowCredentials = "true")
public class UserController {

    private final UserService userService;
    public Com com;
    final String URL_LOCAL = "http://localhost:8081/jwt/";

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
    public ResponseEntity<?> signIn(@RequestBody Map param, HttpServletResponse response){ //UserTB -> Map으로 변경
        HashMap<String, Object> map = new HashMap<>();
        String cookie = "";
        try {
            String signInId = param.get("userId").toString();

            String signInPwd = param.get("userPwd").toString();
            String singInGBNo = param.get("serviceNo").toString();
            UserTb userEntity = userService.findByUserIdReturnUser(signInId);

            if(userEntity == null) {
                map.putAll(com.inputMap(false,"ID를 다시입력해주세요.",signInId));
                return new ResponseEntity<>(map, HttpStatus.OK); //200
            }else if(!userEntity.getUserPwd().equalsIgnoreCase(signInPwd)){
                map.putAll(com.inputMap(false,"PWD를 다시입력해주세요.",signInPwd));
                return new ResponseEntity<>(map, HttpStatus.OK); //200
            }

            ///////////////////////////token api
            RestTemplate restTemplate = new RestTemplate();

            MultiValueMap<String, String> tokenMap = new LinkedMultiValueMap<String,String>();
            tokenMap.add("userId" , signInId);
            tokenMap.add("userPwd", signInPwd);
            tokenMap.add("serviceNo"   , singInGBNo);

            TokenDto result = restTemplate.postForObject(URL_LOCAL+"signIn", tokenMap, TokenDto.class) ;

            System.out.println(result);
            /////////////////////////////////////////////////////////////////////////////////
            if(!userService.checkRefreshToken(result.getRefreshTokenKey())){
                map.putAll(com.inputMap(false,"중복된 TOKEN이 있습니다.",signInId));
                return new ResponseEntity<>(map, HttpStatus.OK); //200
            }
            ResponseCookie responseCookie = Com.createCookie(result.getRefreshTokenKey());
            cookie = responseCookie.toString();

            response.addHeader("Set-Cookie",cookie.toString());
            if(result != null) {
                map.putAll(com.inputMap(true,"로그인 성공",result.getAccessToken()));
            }else{
                map.putAll(com.inputMap(false,"token 발급 오류",signInId));
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

    @PostMapping("/searchId")
    public ResponseEntity<?> searchByUserId(@RequestParam String id){

        return new ResponseEntity<>(userService.findByUserIdReturnUser(id).getUserId(), HttpStatus.OK); //200
    }

    @GetMapping("/vaildToken")
    public ResponseEntity<?> vaildUser(HttpServletRequest request){
        HashMap<String, Object> map = new HashMap<>();
        String text = request.getHeader("Authorization");
        String token = text.split(" ")[1];
        boolean vaildToken = userService.vaildUser(token);
        if(vaildToken){
            map.putAll(com.inputMap(vaildToken,
                    "사용 가능한 TOKEN 입니다.",
                    userService.findByUserIdVaild(userService.findUserByToken(token))
                    )
            );
        }else {
            map.putAll(com.inputMap(vaildToken,"사용할 수 없는 TOKEN 입니다.",token));
            return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED); //401
        }

        return new ResponseEntity<>(map, HttpStatus.OK); //200
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody Map map, HttpServletRequest request){
        //REFRESH TOKEN 재발급하여 리턴
        TokenDto tokenDto = userService.issueToken(request, map);
        return new ResponseEntity<>(Com.inputMap(true,
                                                "TOKEN 발급 성공.",
                                                         tokenDto.getAccessToken())
                                     , HttpStatus.OK); //200
    }

    @GetMapping("/signOut")
    public ResponseEntity<?> signOut(@RequestBody Map reMap){
        HashMap<String, Object> map = new HashMap<>();
        boolean check = userService.signOut(reMap);
        if(check){
            map.putAll(com.inputMap(check,"로그아웃 성공",reMap.get("userId")));
        }else{
            map.putAll(com.inputMap(check,"로그아웃 실패",reMap.get("userId")));
        }

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/friendList")
    public ResponseEntity<?> friendList(@RequestParam long uesrSq){
        ResponseDto responseDto = Com.createResponseDto(true,"친구목록 조회 성공",userService.friendList(uesrSq));
        return  new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PatchMapping("/profile/image")
    public ResponseEntity<?> profileImage(@RequestPart(value = "file") MultipartFile files,@RequestPart(value = "userSq") long userSq) throws IOException {
        return new ResponseEntity<>(Com.createResponseDto(true,
                                                    "친구목록 조회 성공",
                                                        userService.profileImage(files,userSq)),
                HttpStatus.OK);
    }

    @PatchMapping(value = "/profile/msg")
    public ResponseEntity<?> profileMsg(@RequestBody Map map, HttpServletRequest request) {
        vaildToken(request);
        int val = userService.profileMsg(Long.valueOf(map.get("userSq").toString()),String.valueOf(map.get("userMsg")));
        if(val>0){
            return new ResponseEntity<>(Com.createResponseDto(true,"상태명 변경 완료",val)
                , HttpStatus.OK);
        }else{
            return new ResponseEntity<>(Com.createResponseDto(false,"상태명 변경 실패",val)
                , HttpStatus.OK);
        }

    }
//ㅇㄴㅁㅇㅁㄴㅇ
    @PatchMapping(value = "/profile/name")
    public ResponseEntity<?> profileName(@RequestBody Map map, HttpServletRequest request) {
        vaildToken(request);
        int val = userService.profileMsg(Long.parseLong(map.get("userSq").toString()),String.valueOf(map.get("userNm")));
        if(val>0){
            return new ResponseEntity<>(Com.createResponseDto(true,"이름 변경 완료",val)
                    , HttpStatus.OK);
        }else{
            return new ResponseEntity<>(Com.createResponseDto(false,"이름 변경 실패",val)
                    , HttpStatus.OK);
        }

    }

    public ResponseEntity<?> vaildToken(HttpServletRequest request){
        String text = request.getHeader("Authorization");
        String token = text.split(" ")[1];

        if(!userService.vaildUser(token)){
            return new ResponseEntity<>(Com.createResponseDto(false,"사용할 수 없는 TOKEN 입니다.","")
                    , HttpStatus.UNAUTHORIZED);
        }else{
            return null;
        }
    }

}
