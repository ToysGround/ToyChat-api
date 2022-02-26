package com.api.user.domain.repository;

import com.api.user.domain.entity.UserProfileImageTbEntity;
import com.api.user.domain.entity.UserTb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserProfileImageRepository extends JpaRepository<UserProfileImageTbEntity, Long> {
    @Query("update UserProfileImageTbEntity u " +
            "set u.fileName = :fileName, " +
                "u.originalName = :originalName, " +
                "u.fileUrl = :fileUrl " +
            "where u.userSq = :target")
    UserProfileImageTbEntity updateImage(@Param("target") long userSq,
                                         @Param("fileName") String fileName,
                                         @Param("originalName") String originalName,
                                         @Param("fileUrl") String fileUrl);
}