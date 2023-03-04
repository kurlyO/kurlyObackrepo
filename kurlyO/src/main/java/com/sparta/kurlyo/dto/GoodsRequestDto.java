package com.sparta.kurlyo.dto;

import com.sparta.kurlyo.entity.Category;
import com.sparta.kurlyo.entity.Packaging;
import lombok.Getter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
public class GoodsRequestDto {
    private String goodsName;
    private int price;
    private String summary;
    private String category;
    private String packaging;
}
