package com.api.user.domain.entity;

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
    private Integer imageNum;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserImageTb that = (UserImageTb) o;

        if (userSq != that.userSq) return false;
        if (imageNum != null ? !imageNum.equals(that.imageNum) : that.imageNum != null) return false;
        if (!Arrays.equals(image1, that.image1)) return false;
        if (!Arrays.equals(image2, that.image2)) return false;
        if (!Arrays.equals(image3, that.image3)) return false;
        if (!Arrays.equals(image4, that.image4)) return false;
        if (!Arrays.equals(image5, that.image5)) return false;
        if (!Arrays.equals(image6, that.image6)) return false;
        if (!Arrays.equals(image7, that.image7)) return false;
        if (!Arrays.equals(image8, that.image8)) return false;
        if (!Arrays.equals(image9, that.image9)) return false;
        if (!Arrays.equals(image10, that.image10)) return false;

        return true;
    }

}
