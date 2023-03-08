package com.sparta.kurlyo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class CartBoughtRequestDto {

    List<Long> cartIdList = new ArrayList<>();
}