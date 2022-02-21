package com.api.user.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "user_option_tb", schema = "toy_chat")
public class UserOptionTb {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "USER_SQ")
    private long userSq;
    @Basic
    @Column(name = "THEMA")
    private String thema;
    @Basic
    @Column(name = "MENU_LOC")
    private String menuLoc;
    @Basic
    @Column(name = "FONT_SIZE")
    private Integer fontSize;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserOptionTb that = (UserOptionTb) o;

        if (userSq != that.userSq) return false;
        if (thema != null ? !thema.equals(that.thema) : that.thema != null) return false;
        if (menuLoc != null ? !menuLoc.equals(that.menuLoc) : that.menuLoc != null) return false;
        if (fontSize != null ? !fontSize.equals(that.fontSize) : that.fontSize != null) return false;

        return true;
    }

}
