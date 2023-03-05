package com.sparta.kurlyo.dto;

import lombok.Getter;

@Getter
public class GoodsRequestDto {
    private String goodsName;
    private Integer price;
    private String summary;
    private String category;
    private String packaging;
    private Integer count;
}
