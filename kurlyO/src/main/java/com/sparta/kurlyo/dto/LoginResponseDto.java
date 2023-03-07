package com.sparta.kurlyo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
//@NoArgsConstructor
public class LoginResponseDto {
    private String name;

    public static LoginResponseDto of(String name) {
        return LoginResponseDto.builder()
                .name(name)
                .build();
    }
}
