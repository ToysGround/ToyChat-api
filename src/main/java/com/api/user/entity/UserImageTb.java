package com.api.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Arrays;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "user_image_tb", schema = "toy_chat")
public class UserImageTb {
    @Basic
    @Id
    @Column(name = "USER_SQ")
    private int userSq;
    @Basic
    @Column(name = "IMAGE_NUM")
    private String imageNum;
    @Basic
    @Column(name = "IMAGE_1")
    private byte[] image1;
    @Basic
    @Column(name = "IMAGE_2")
    private byte[] image2;
    @Basic
    @Column(name = "IMAGE_3")
    private byte[] image3;
    @Basic
    @Column(name = "IMAGE_4")
    private byte[] image4;
    @Basic
    @Column(name = "IMAGE_5")
    private byte[] image5;
    @Basic
    @Column(name = "IMAGE_6")
    private byte[] image6;
    @Basic
    @Column(name = "IMAGE_7")
    private byte[] image7;
    @Basic
    @Column(name = "IMAGE_8")
    private byte[] image8;
    @Basic
    @Column(name = "IMAGE_9")
    private byte[] image9;
    @Basic
    @Column(name = "IMAGE_10")
    private byte[] image10;

}
