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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FriendTb that = (FriendTb) o;

        if (userSq != that.userSq) return false;
        if (toUserSq != that.toUserSq) return false;
        if (friendSt != null ? !friendSt.equals(that.friendSt) : that.friendSt != null) return false;
        if (statDt != null ? !statDt.equals(that.statDt) : that.statDt != null) return false;
        if (regDt != null ? !regDt.equals(that.regDt) : that.regDt != null) return false;

        return true;
    }

}
