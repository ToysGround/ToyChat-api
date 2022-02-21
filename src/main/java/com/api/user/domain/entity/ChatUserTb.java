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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChatUserTb that = (ChatUserTb) o;

        if (chatUserSq != that.chatUserSq) return false;
        if (roomSq != 0 ? !(roomSq == that.roomSq) : that.roomSq != 0) return false;
        if (userSq != 0 ? !(userSq == that.userSq) : that.userSq != 0) return false;
        if (regDt != null ? !regDt.equals(that.regDt) : that.regDt != null) return false;

        return true;
    }

}
