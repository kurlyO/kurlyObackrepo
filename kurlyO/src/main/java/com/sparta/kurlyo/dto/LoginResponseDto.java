package com.sparta.kurlyo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginResponseDto<T> {
    private String name;
    private T token;

    public LoginResponseDto(String name, T token) {
        this.name = name;
        this.token = token;
    }
}
