package com.api.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "friend_tb", schema = "toy_chat")
public class FriendTb {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "USER_SQ")
    private long userSq;
    @Basic
    @Column(name = "TO_USER_SQ")
    private long toUserSq;
    @Basic
    @Column(name = "FRIEND_ST")
    private String friendSt;
    @Basic
    @Column(name = "STAT_DT")
    private Timestamp statDt;
    @Basic
    @Column(name = "REG_DT")
    private Timestamp regDt;


}
