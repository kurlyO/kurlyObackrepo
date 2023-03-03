package com.sparta.kurlyo.controller;

import com.sparta.kurlyo.dto.GoodsResponseDto;
import com.sparta.kurlyo.dto.ResponseDto;
import com.sparta.kurlyo.security.UserDetailsImpl;
import com.sparta.kurlyo.service.GoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class GoodsController {

    private final GoodsService goodsService;

    @GetMapping("/goods/{goodsId}")
    public GoodsResponseDto getDetails(@PathVariable long goodsId) {
        return goodsService.getDetails(goodsId);
    }

    @GetMapping("/categories")
    public ResponseDto<List<GoodsResponseDto>> getCategoriesList(@RequestParam int page, int size, @RequestParam(defaultValue = "createdAt")String sortBy, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return GoodsService.getCategoriesList(page-1, size, sortBy);
    }
}
