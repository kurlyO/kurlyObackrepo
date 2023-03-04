package com.sparta.kurlyo.controller;

import com.sparta.kurlyo.dto.CartRequestDto;
import com.sparta.kurlyo.dto.CartResponseDto;
import com.sparta.kurlyo.dto.CartWholeResponseDto;
import com.sparta.kurlyo.dto.Response;
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

    @PostMapping("/cart/{goodsId}")
    public ResponseEntity<Response> addCart(@PathVariable long goodsId,
                                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return cartService.addCart(goodsId, userDetails.getUsername());
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


        return cartService.updateGoodsCart(cartId, requestDto, userDetails);
    }

    @DeleteMapping("/{cartId}")
    public void deleteComment(@PathVariable Long cartId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        cartService.deleteGoodsCart(cartId,userDetails);
    }

}
