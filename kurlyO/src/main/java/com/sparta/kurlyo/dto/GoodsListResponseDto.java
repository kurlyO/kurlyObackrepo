package com.sparta.kurlyo.dto;

import com.sparta.kurlyo.entity.Goods;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GoodsListResponseDto {
    private long goodsId;
    private String goodsName;
    private int price;
    private String summary;
    private String image;
    private Integer count;

    public static GoodsListResponseDto of(Goods goods) {
        return GoodsListResponseDto.builder()
                .goodsId(goods.getId())
                .goodsName(goods.getGoodsName())
                .price(goods.getPrice())
                .count(goods.getCount())
                .summary(goods.getSummary())
                .image(goods.getImage())
                .build();
    }
}
