package com.sparta.kurlyo.controller;

import com.sparta.kurlyo.dto.*;
import com.sparta.kurlyo.entity.Members;
import com.sparta.kurlyo.security.UserDetailsImpl;
import com.sparta.kurlyo.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // CART 담기
    @PostMapping("/cart")
    public ResponseEntity<Response> addCart(@RequestParam("goodsId") long goodsId,
                                            @RequestParam("amount") int amount,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return cartService.addCart(goodsId, amount, userDetails.getUsername());
    }

    @GetMapping("/cart")
    public CartWholeResponseDto getCart(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        // userDetails가 존재하는지 확인
        if (userDetails == null) {
            throw new IllegalArgumentException("사용자가 존재하지 않습니다");
        }

        Members members = userDetails.getMember();
        return cartService.getCart(members);
    }

    @PutMapping("/cart/amount/{cartId}")
    public CartResponseDto updateCart (
            @PathVariable Long cartId,
            @RequestBody CartRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {


        return cartService.updateGoodsCart(cartId, requestDto, userDetails.getMember());
    }

    @DeleteMapping("/cart/{cartId}")
    public ResponseEntity<Response> deleteComment(@PathVariable Long cartId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return cartService.deleteGoodsCart(cartId,userDetails.getMember());
    }
    @PostMapping("/cart/bought/{cartId}")
    public ResponseEntity<Response> BuyComment(@PathVariable Long cartId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return cartService.BuyGoodsCart(cartId,userDetails.getMember());
    }

}
