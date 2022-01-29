package com.api.user.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "USER_TB")
public class UserTb {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_SQ", nullable = false)
    private Integer id;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "USER_PWD")
    private String userPwd;

    @Column(name = "USER_NM", length = 20)
    private String userNm;

    @Column(name = "USER_SEX")
    private Character userSex;

    @Column(name = "USER_HP")
    private Integer userHp;

    @Column(name = "USER_BIRTH")
    private Integer userBirth;

    @Column(name = "USER_ST")
    private Character userSt;

    @Column(name = "USER_GB")
    private Character userGb;

    @Column(name = "REG_DT")
    private Instant regDt;

    public Instant getRegDt() {
        return regDt;
    }

    public void setRegDt(Instant regDt) {
        this.regDt = regDt;
    }

    public Character getUserGb() {
        return userGb;
    }

    public void setUserGb(Character userGb) {
        this.userGb = userGb;
    }

    public Character getUserSt() {
        return userSt;
    }

    public void setUserSt(Character userSt) {
        this.userSt = userSt;
    }

    public Integer getUserBirth() {
        return userBirth;
    }

    public void setUserBirth(Integer userBirth) {
        this.userBirth = userBirth;
    }

    public Integer getUserHp() {
        return userHp;
    }

    public void setUserHp(Integer userHp) {
        this.userHp = userHp;
    }

    public Character getUserSex() {
        return userSex;
    }

    public void setUserSex(Character userSex) {
        this.userSex = userSex;
    }

    public String getUserNm() {
        return userNm;
    }

    public void setUserNm(String userNm) {
        this.userNm = userNm;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}