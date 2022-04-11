package com.api.user.domain.repository;

import com.api.user.domain.entity.UserTb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface UserRepository extends JpaRepository<UserTb, Long>{

    @Query("select u from UserTb u where u.userId = :target")
    UserTb findByUserId(@Param("target") String userId);

    @Query("select u.userSq, u.userId, u.userNm,u.userMsg, u.userImage from UserTb u where u.userId = :target")
    UserTb findByUserIdVaild(@Param("target") String userId);

    @Query("select u.userSq, u.userId, u.userNm,u.userMsg, u.userImage from UserTb u where u.userId in (:target)")
    List<UserTb> findByUserIdFriends(@Param("target") List<Long> userId);

    @Modifying
    @Query("update UserTb u set u.userMsg = :value where u.userSq = :target")
    int updateUserMsg(@Param("target") long userSq, @Param("value") String userMsg);

    @Modifying
    @Query("update UserTb u set u.userNm = :value where u.userSq = :target")
    int updateUserName(@Param("target") long userSq, @Param("value") String userNm);


}