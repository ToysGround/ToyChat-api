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
@Table(name = "chat_message_tb", schema = "toy_chat")
public class ChatMessageTb {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "CHAT_MSG_SQ")
    private long chatMsgSq;
    @Basic
    @Column(name = "ROOM_SQ")
    private long roomSq;
    @Basic
    @Column(name = "USER_SQ")
    private long userSq;
    @Basic
    @Column(name = "CHAT_MSG")
    private String chatMsg;
    @Basic
    @Column(name = "REG_DT")
    private Timestamp regDt;

}
