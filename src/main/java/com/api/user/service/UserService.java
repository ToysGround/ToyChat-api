package com.api.user.service;


import com.api.user.controller.dto.TokenDto;
import com.api.user.domain.entity.UserTb;
import com.api.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class UserService {

    private  final UserRepository userRepository;
    final String URL_LOCAL = "http://localhost:8081/jwt/";

    @Transactional
    public String insert(UserTb user){
        System.out.println(user.toString());
        UserTb userEntity = userRepository.save(user);
        return userEntity.getUserId();
    }

    @Transactional(readOnly = true)
    public UserTb findOne(long sq){
        return userRepository.findById(sq)
                .orElseThrow(()->new IllegalArgumentException("id를 확인해주세요"));
    }

    @Transactional(readOnly = true)
    public List<UserTb> findAll(){
        return userRepository.findAll();
    }

    @Transactional
    public UserTb modify(long sq, UserTb user){
        UserTb userEntity = userRepository.findById(sq)
                .orElseThrow(()->new IllegalArgumentException("id를 확인해주세요"));
        userEntity.setUserNm(user.getUserNm());
        userEntity.setUserHp(user.getUserHp());
        return userEntity;
    }

    @Transactional
    public String delete(long sq){
        userRepository.deleteById(sq);
        return "ok";
    }


    @Transactional(readOnly = true)
    public boolean findByUserId(String userId){
        UserTb userEntity = userRepository.findByUserId(userId);
        if(userEntity == null || userEntity.getUserId() == ""  )return true;
        return false;
    }

    @Transactional(readOnly = true)
    public UserTb findByUserIdReturnUser(String userId){
        return userRepository.findByUserId(userId);
    }

    public boolean vaildUser(String token){
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> tokenMap = new LinkedMultiValueMap<String,String>();
        tokenMap.add("token" , token);
        boolean result = restTemplate.postForObject(URL_LOCAL+"vaild", tokenMap, boolean.class) ;
        System.out.println(result);
        return result;
    }

    public TokenDto issueToken(HttpServletRequest request, Map map){
        MultiValueMap<String, String> tokenMap = new LinkedMultiValueMap<String,String>();
        String accessToken = request.getHeader("Authorization").split(" ")[1];
        tokenMap.setAll(map);
        tokenMap.add("accessToken" , accessToken);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> entity = new HttpEntity<>(tokenMap,headers);

        TokenDto result = restTemplate.postForObject(URL_LOCAL+"refresh", entity, TokenDto.class) ;
        System.out.println(result);
        return result;
    }

    public boolean signOut(Map map){
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> tokenMap = new LinkedMultiValueMap<String,String>();
        tokenMap.setAll(map);
        boolean result = restTemplate.postForObject(URL_LOCAL+"signOut", tokenMap, boolean.class) ;
        return result;
    }

    public boolean checkRefreshToken(String refreshTokenKey){
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> tokenMap = new LinkedMultiValueMap<String,String>();

        tokenMap.add("refreshTokenKey", refreshTokenKey);
        boolean result = restTemplate.postForObject(URL_LOCAL+"checkRefresh", tokenMap, boolean.class) ;

        return result;
    }

}
