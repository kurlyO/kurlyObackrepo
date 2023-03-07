package com.sparta.kurlyo.dto;

import com.sparta.kurlyo.entity.Goods;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GoodsResponseDto {
    private Long goodsId;
    private String goodsName;
    private int price;
    private String summary;
    private String image;
    private String packaging;
    private String category;

    public static GoodsResponseDto of(Goods goods) {
        return GoodsResponseDto.builder()
                .goodsId(goods.getId())
                .goodsName(goods.getGoodsName())
                .price(goods.getPrice())
                .summary(goods.getSummary())
                .image(goods.getImage())
                .packaging(goods.getPackaging().getKorean())
                .category(goods.getCategory().getName())
                .build();
    }


}
