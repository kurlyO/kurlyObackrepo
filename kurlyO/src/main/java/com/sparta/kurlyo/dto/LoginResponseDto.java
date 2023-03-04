package com.sparta.kurlyo.dto;

public class LoginResponseDto<T> {
    private String name;
    private T token;

    public LoginResponseDto(String name, T token) {
        this.name = name;
        this.token = token;
    }
}
