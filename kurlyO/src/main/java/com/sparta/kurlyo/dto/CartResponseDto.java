package com.sparta.kurlyo.dto;

import com.sparta.kurlyo.entity.Cart;
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

    public CartResponseDto(Cart cart) {
        this.cartId = cart.getId();
        this.goodsId = cart.getGoods().getId();
        this.image = cart.getGoods().getImage();
        this.goodsName = cart.getGoods().getGoodsName();
        this.price = cart.getGoods().getPrice();
        this.amount = cart.getAmount();
        this.createdAt = cart.getCreateAt();
        this.modifiedAt = cart.getModifiedAt();
    }


    public static CartResponseDto of (Cart cart) {

        return new CartResponseDto(cart);
    }
}