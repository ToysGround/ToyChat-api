package com.api.user.service;


import com.api.user.controller.dto.TokenDto;
import com.api.user.controller.dto.UserDto;
import com.api.user.domain.entity.FriendTb;
import com.api.user.domain.entity.UserImageTb;
import com.api.user.domain.entity.UserProfileImageTbEntity;
import com.api.user.domain.entity.UserTb;
import com.api.user.domain.repository.FriendRepository;
import com.api.user.domain.repository.UserImageRepository;
import com.api.user.domain.repository.UserProfileImageRepository;
import com.api.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
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
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    @Transactional(readOnly = true)
    public UserDto findByUserIdVaild(String userId){
        UserTb userTb =userRepository.findByUserId(userId);
        return  UserDto.builder()
                .userSq(userTb.getUserSq())
                .userId(userTb.getUserId())
                .userNm(userTb.getUserNm())
                .userMsg(userTb.getUserMsg())
                .userImage(userTb.getUserImage())
                .build();
    }

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
            //imageTb.setFileData();
            return userProfileImageRepository.save(imageTb);
        }else{
            return userProfileImageRepository.updateImage(id,destinationFileName,sourceFileName,filePath);
        }
    }

    @Transactional(readOnly = false)
    public String profileImage2(MultipartFile files, long userSq) throws Exception {
        System.out.println("##### :: " + files.getBytes());
        String test = java.util.Base64.getEncoder().encodeToString(files.getBytes());
        byte[] test2 = files.getBytes();
        //System.out.println("#### :::::::: " + test);
        String imageUrl = files.getOriginalFilename();
        String result = "";

        String filePathName = files.getOriginalFilename();
        String fileExtName  = FilenameUtils.getExtension(filePathName).toLowerCase();

        if( imageUrl.length() > 0 )
        {
            int imageUrlLength = imageUrl.length();
            String imageString ;

                FileInputStream inputStream = null;
                ByteArrayOutputStream byteOutStream = null;
                try
                {
                    File file = new File( filePathName );
                    if( file.exists() )
                    {
                        inputStream = new FileInputStream( file );
                        byteOutStream = new ByteArrayOutputStream();
                        int len = 0;
                        byte[] buf = new byte[1024];
                        while( (len = inputStream.read( buf )) != -1 ) {
                            byteOutStream.write(buf, 0, len);
                        }
                        byte[] fileArray = byteOutStream.toByteArray();
                        imageString = new String( Base64.encodeBase64( fileArray ) );
//                        String changeString = "data:image/" + fileExtName + ";base64, " + imageString;
//                        content = content.replace(imageUrl[i], changeString);
                        result = imageString;
                    }
                }
                catch( IOException e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    //inputStream.close();
                  //  byteOutStream.close();
                }
            }
       // FileUtils.readFileToByteArray(files);
        UserProfileImageTbEntity imageTb = new UserProfileImageTbEntity();
        if(!userProfileImageRepository.findById(userSq).isEmpty()){
            imageTb = userProfileImageRepository.findById(userSq).get();
        }
        long id = imageTb.getUserSq();

        if(id == 0){
            imageTb.setUserSq(userSq);
            imageTb.setFileName("");
            imageTb.setOriginalName(filePathName);
            imageTb.setFileData(test2);
            userProfileImageRepository.save(imageTb);
        }else{
            userProfileImageRepository.updateImage(id,"",filePathName,"");
        }
         System.out.println("@@@@@@@@@@@@@@@@@@ : " + result);
        return result;
    }

    @Transactional()
    public int profileMsg(long userSq, String userMsg){
        return userRepository.updateUserMsg(userSq,userMsg);
    }

    @Transactional()
    public int profileName(long userSq, String userNm){
        return userRepository.updateUserName(userSq,userNm);
    }

    @Transactional(readOnly = true)
    public UserDto findByUserIdUserInfo(long userSq){
        Optional<UserTb> userTb = userRepository.findById(userSq);
        return  UserDto.builder()
                .userSq(userTb.get().getUserSq())
                .userId(userTb.get().getUserId())
                .userNm(userTb.get().getUserNm())
                .userMsg(userTb.get().getUserMsg())
                .userImage(userTb.get().getUserImage())
                .build();
    }

    @Transactional(readOnly = false)
    public void insertUserFriendByuserSq(long fromSq, long toSq){
        FriendTb friendTb = new FriendTb();
        friendTb.setUserSq(fromSq);
        friendTb.setToUserSq(toSq);
        friendRepository.save(friendTb);
    }




}
