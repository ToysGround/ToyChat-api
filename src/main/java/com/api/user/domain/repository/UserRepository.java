package com.api.user.domain.repository;


import com.api.user.domain.entity.UserTb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UserRepository extends JpaRepository<UserTb, Long>{

    @Query("select u from UserTb u where u.userId = :target")
    UserTb findByUserId(@Param("target") String userId);

    @Query("select u.userSq, u.userId, u.userNm,u.userMsg, u.userImage from UserTb u where u.userId = :target")
    UserTb findByUserIdVaild(@Param("target") String userId);

}