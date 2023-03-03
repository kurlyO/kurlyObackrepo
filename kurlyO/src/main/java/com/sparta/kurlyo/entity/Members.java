package com.sparta.kurlyo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Members {
    @Id @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String memberName;

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

//    public void changeRole(UserRoleEnum roleEnum) {
//        this.role = roleEnum;
//    }
}
