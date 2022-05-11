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
@Table(name = "chat_user_tb", schema = "toy_chat")
public class ChatUserTb {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "CHAT_USER_SQ")
    private long chatUserSq;
    @Basic
    @Column(name = "ROOM_SQ")
    private long roomSq;
    @Basic
    @Column(name = "USER_SQ")
    private long userSq;
    @Basic
    @Column(name = "REG_DT")
    private Timestamp regDt;


}
