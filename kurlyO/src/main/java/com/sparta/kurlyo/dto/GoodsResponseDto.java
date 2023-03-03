package com.sparta.kurlyo.dto;

import com.sparta.kurlyo.entity.Goods;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GoodsResponseDto {
    private long goodsId;
    private String goodsName;
    private int price;
    private String summary;
    private String image;
    private String packaging;
    private String category;

    public GoodsResponseDto(Goods goods) {
        this.goodsId = goods.getId();
        this.goodsName = goods.getGoodsName();
        this.price = goods.getPrice();
        this.summary = goods.getSummary();
        this.image = goods.getImage();
        this.packaging = goods.getPackaging().name();
        this.category = goods.getCategory().getName();
    }
}
