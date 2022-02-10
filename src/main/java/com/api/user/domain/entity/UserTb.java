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
    private long id;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "USER_PWD")
    private String userPwd;

    @Column(name = "USER_NM", length = 20)
    private String userNm;

    @Column(name = "USER_SEX")
    //@Enumerated(EnumType.ORDINAL)
    private String userSex;

    @Column(name = "USER_HP")
    private String userHp;

    @Column(name = "USER_BIRTH")
    private String userBirth;

    @Column(name = "USER_ST")
    //@Enumerated(EnumType.ORDINAL)
    private String userSt;

    @Column(name = "USER_GB")
    //@Enumerated(EnumType.ORDINAL)
    private String userGb;

    @Column(name = "REG_DT")
    private Instant regDt;

}