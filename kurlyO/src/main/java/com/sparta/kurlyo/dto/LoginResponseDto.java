package com.sparta.kurlyo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginResponseDto {
    private String name;

    public LoginResponseDto(String name) {
        this.name = name;
    }
}
