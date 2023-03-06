package com.sparta.kurlyo.entity;

import lombok.Getter;

@Getter
public enum Packaging {
    ROOM_TEMP("상온"),
    COLD("냉장"),
    FROZEN("냉동");

    private final String korean;

    Packaging(String korean) {
        this.korean = korean;
    }
}
