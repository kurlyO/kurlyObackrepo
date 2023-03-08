package com.sparta.kurlyo.dto;

import com.sparta.kurlyo.entity.Cart;
import com.sparta.kurlyo.entity.Goods;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CartResponseDto {

    private Long cartId;
    private Long goodsId;
    private String image;
    private String goodsName;
    private int price;
    private int amount;
    private int goodsCount;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public CartResponseDto(Cart cart, Goods goods) {
        this.cartId = cart.getId();
        this.goodsId = goods.getId();
        this.image = goods.getImage();
        this.goodsName = goods.getGoodsName();
        this.price = goods.getPrice();
        this.amount = cart.getAmount();
        this.goodsCount = goods.getCount();
        this.createdAt = cart.getCreateAt();
        this.modifiedAt = cart.getModifiedAt();
    }


    public static CartResponseDto of(Cart cart) {
        return new CartResponseDto(cart, cart.getGoods());
    }
}