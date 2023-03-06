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
    private Integer count;

    public GoodsResponseDto(Goods goods) {
        this.goodsId = goods.getId();
        this.goodsName = goods.getGoodsName();
        this.price = goods.getPrice();
        this.summary = goods.getSummary();
        this.image = goods.getImage();
        this.count = goods.getCount();
        this.packaging = goods.getPackaging().getKorean();
        this.category = goods.getCategory().getName();
    }
}
