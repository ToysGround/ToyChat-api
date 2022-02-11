package com.api.user.controller;

import com.api.user.common.Com;
import com.api.user.controller.dto.TokenDto;
import com.api.user.domain.entity.UserTb;
import com.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
@CrossOrigin
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
    //public ResponseEntity<?> signIn(@RequestBody Map param){ //UserTB -> Map으로 변경
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
           // List<HttpMessageConverter<?>> converts = new ArrayList<HttpMessageConverter<?>>();
           // converts.add(new FormHttpMessageConverter());
            //converts.add(new StringHttpMessageConverter());

            RestTemplate restTemplate = new RestTemplate();
           // restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
            //restTemplate.setMessageConverters(converts);

            MultiValueMap<String, String> tokenMap = new LinkedMultiValueMap<String,String>();
            tokenMap.add("userId" , signInId);
            tokenMap.add("userPwd", signInPwd);
            tokenMap.add("serviceNo"   , singInGBNo);

            /*Charset utf8 = Charset.forName("UTF-8");
            MediaType mediaType = new MediaType("application", "json", utf8);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(mediaType);

            HttpEntity<Map> entity = new HttpEntity<>(tokenMap, headers);*/

            System.out.println("*************************************");
            TokenDto result = restTemplate.postForObject(URL_LOCAL+"signIn", tokenMap, TokenDto.class) ;

            System.out.println(result);
            /////////////////////////////////////////////////////////////////////////////////
            ResponseCookie responseCookie = Com.createCookie(result.getRefreshTokenKey());
            Cookie cookie1 = new Cookie("refreshTokenKey",result.getRefreshTokenKey());
            cookie1.setPath("/");
           /* cookie = responseCookie.toString();
            System.out.println(cookie);*/
            response.addCookie(cookie1);
           // response.addHeader("Set-Cookie",cookie.toString());
            if(result != null) {
                map.putAll(com.inputMap(true,"로그인 성공",result.getAccessToken()));
            }else{
                map.putAll(com.inputMap(false,"token 발급 오류",signInId));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(map, HttpStatus.OK); //200
        /*return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie)
                .body(map);*/
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

        //return new ResponseEntity<>(userService.findByUserIdReturnUser(id), HttpStatus.OK); //200
        return new ResponseEntity<>(userService.findByUserIdReturnUser(id).getUserId(), HttpStatus.OK); //200
    }

}
