package com.api.user.entity;

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


}
