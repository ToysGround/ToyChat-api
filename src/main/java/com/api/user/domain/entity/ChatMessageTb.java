package com.api.user.domain.entity;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChatMessageTb that = (ChatMessageTb) o;

        if (chatMsgSq != that.chatMsgSq) return false;
        if (roomSq != 0 ? !(roomSq == that.roomSq) : that.roomSq != 0) return false;
        if (userSq != 0 ? !(userSq == that.userSq) : that.userSq != 0) return false;
        if (chatMsg != null ? !chatMsg.equals(that.chatMsg) : that.chatMsg != null) return false;
        if (regDt != null ? !regDt.equals(that.regDt) : that.regDt != null) return false;

        return true;
    }

}
