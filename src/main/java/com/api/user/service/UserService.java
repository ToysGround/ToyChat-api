package com.api.user.service;


import com.api.user.controller.dto.TokenDto;
import com.api.user.domain.entity.UserImageTb;
import com.api.user.domain.entity.UserProfileImageTbEntity;
import com.api.user.domain.entity.UserTb;
import com.api.user.domain.repository.FriendRepository;
import com.api.user.domain.repository.UserImageRepository;
import com.api.user.domain.repository.UserProfileImageRepository;
import com.api.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class UserService {

    private  final UserRepository userRepository;
    private  final FriendRepository friendRepository;
    private  final UserProfileImageRepository userProfileImageRepository;
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

    @Transactional(readOnly = false)
    public UserTb findByUserIdVaild(String userId){  return userRepository.findByUserIdVaild(userId); }

    public boolean vaildUser(String token){
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> tokenMap = new LinkedMultiValueMap<String,String>();
        tokenMap.add("token" , token);
        boolean result = restTemplate.postForObject(URL_LOCAL+"vaild", tokenMap, boolean.class) ;
        System.out.println(result);
        return result;
    }

    public String findUserByToken(String token){
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> tokenMap = new LinkedMultiValueMap<String,String>();
        tokenMap.add("token" , token);
        String result = restTemplate.postForObject(URL_LOCAL+"getUser", tokenMap, String.class) ;
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
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> entity = new HttpEntity<>(tokenMap,headers);

        tokenMap.add("refreshTokenKey", refreshTokenKey);
        boolean result = restTemplate.postForObject(URL_LOCAL+"checkRefresh", entity, boolean.class) ;

        return result;
    }

    @Transactional(readOnly = true)
    public List<UserTb> friendList(long sq){
        List<Long> friendTb = friendRepository.findByUserSqList(sq)
                .orElseThrow(()->new IllegalArgumentException("회원번호를 확인해주세요"));
        return  userRepository.findByUserIdFriends(friendTb);
    }

    @Transactional(readOnly = false)
    public UserProfileImageTbEntity profileImage(MultipartFile files, long userSq) throws IOException {
        UserProfileImageTbEntity imageTb = new UserProfileImageTbEntity();
        int imgNum = 0;

        if(!userProfileImageRepository.findById(userSq).isEmpty()){
            imageTb = userProfileImageRepository.findById(userSq).get();
        }
        long id = imageTb.getUserSq();

        String sourceFileName = files.getOriginalFilename();
        String sourceFileNameExtension = FilenameUtils.getExtension(sourceFileName).toLowerCase();
        FilenameUtils.removeExtension(sourceFileName);

        File destinationFile;
        String destinationFileName;
        //파일경로 환경 맞춰야함
        String filePath = "C:\\Users\\Jin\\IdeaProjects\\ToyChat\\src\\main\\java\\com\\api\\user\\image";

        do {
            // 이름 암호화
            destinationFileName = RandomStringUtils.randomAlphanumeric(32) + "." + sourceFileNameExtension;
            destinationFile = new File(filePath + destinationFileName);
        }while (destinationFile.exists());

        if(id == 0){
            imageTb.setFileName(destinationFileName);
            imageTb.setOriginalName(sourceFileName);
            imageTb.setFileUrl(filePath);
            return userProfileImageRepository.save(imageTb);
        }else{
            return userProfileImageRepository.updateImage(id,destinationFileName,sourceFileName,filePath);
        }
    }

    public UserTb profileMsg(Map map){
        return userRepository.updateUserMsg(String.valueOf(map.get("userSq")),String.valueOf(map.get("userMsg")));
    }

}
