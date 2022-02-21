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
@Table(name = "chat_room_tb", schema = "toy_chat")
public class ChatRoomTb {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ROOM_SQ")
    private long roomSq;
    @Basic
    @Column(name = "ROOM_NM")
    private Integer roomNm;
    @Basic
    @Column(name = "REG_DT")
    private Timestamp regDt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChatRoomTb that = (ChatRoomTb) o;

        if (roomSq != that.roomSq) return false;
        if (roomNm != null ? !roomNm.equals(that.roomNm) : that.roomNm != null) return false;
        if (regDt != null ? !regDt.equals(that.regDt) : that.regDt != null) return false;

        return true;
    }

}
