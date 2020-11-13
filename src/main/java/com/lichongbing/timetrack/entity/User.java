package com.lichongbing.timetrack.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by echisan on 2018/6/23
 */
@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    @Column(name = "openid")
    private String openid;

    @Column(name = "phonenumber")
    private String phonenumber;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "gender")
    private String gender;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "avatarurl")
    private String avatarurl;


    @Column(name = "province")
    private String province;
}
