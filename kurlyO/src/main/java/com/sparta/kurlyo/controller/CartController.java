package com.sparta.kurlyo.controller;

import com.sparta.kurlyo.security.UserDetailsImpl;
import com.sparta.kurlyo.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/cart/{goodsId}")
    public String addCart(@PathVariable long goodsId,
                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return cartService.addCart(goodsId, userDetails.getUsername());
    }

}
