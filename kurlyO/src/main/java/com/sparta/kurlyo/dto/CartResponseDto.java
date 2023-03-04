package com.sparta.kurlyo.dto;

import com.sparta.kurlyo.entity.Cart;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CartResponseDto {

    private String image;
    private String goodsName;
    private int price;
    private int amount;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public CartResponseDto(Cart cart) {
        new CartResponseDto(cart);
    }


    public static CartResponseDto of (Cart cart) {
        return new CartResponseDto(cart);
    }
}