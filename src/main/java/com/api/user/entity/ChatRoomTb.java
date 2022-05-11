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
@Table(name = "chat_room_tb", schema = "toy_chat")
public class ChatRoomTb {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ROOM_SQ")
    private long roomSq;
    @Basic
    @Column(name = "ROOM_NM")
    private String roomNm;
    @Basic
    @Column(name = "REG_DT")
    private Timestamp regDt;

}
