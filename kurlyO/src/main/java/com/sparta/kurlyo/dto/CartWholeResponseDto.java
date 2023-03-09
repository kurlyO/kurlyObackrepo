package com.sparta.kurlyo.dto;

import com.sparta.kurlyo.entity.Cart;
import com.sparta.kurlyo.entity.Packaging;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class CartWholeResponseDto {

    private List<CartResponseDto> cold = new ArrayList<>();
    private List<CartResponseDto> frozen = new ArrayList<>();
    private List<CartResponseDto> room_temperature = new ArrayList<>();

    public void addGoodsCart(Cart cart){
        Packaging packaging = cart.getGoods().getPackaging();
        if (packaging.equals(Packaging.COLD)) {
            cold.add(CartResponseDto.of(cart));
        } else if (packaging.equals(Packaging.FROZEN)) {
            frozen.add(CartResponseDto.of(cart));
        } else if (packaging.equals(Packaging.ROOM_TEMP)) {
            room_temperature.add(CartResponseDto.of(cart));
        }
    }
}
