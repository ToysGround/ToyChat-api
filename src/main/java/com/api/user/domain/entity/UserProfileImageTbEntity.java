package com.api.user.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "USER_PROFILE_IMAGE_TB", schema = "toy_chat")
public class UserProfileImageTbEntity {
    @Basic
    @Id
    @Column(name = "USER_SQ")
    private long userSq;
    @Basic
    @Column(name = "ORIGINAL_NAME")
    private String originalName;
    @Basic
    @Column(name = "FILE_NAME")
    private String fileName;
    @Basic
    @Column(name = "FILE_URL")
    private String fileUrl;


}
