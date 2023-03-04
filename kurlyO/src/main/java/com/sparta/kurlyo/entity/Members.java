package com.sparta.kurlyo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Members extends TimeStamped {
    @Id @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String account;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String gender;

    @Column
    private String birth;

    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;

    public Members(String accout, String password, String name, String email, String address, String phone, String gender, String birth, UserRoleEnum role) {
        this.account = accout;
        this.password = password;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.birth = birth;
        this.role = role;
    }

//    public void changeRole(UserRoleEnum roleEnum) {
//        this.role = roleEnum;
//    }
}
