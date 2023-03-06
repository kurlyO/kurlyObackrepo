package com.sparta.kurlyo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GoodsRequestDto {
    private String goodsName;
    private Integer price;
    private String summary;
    private String image;
    private String category;
    private String packaging;
    private Integer count;
}
