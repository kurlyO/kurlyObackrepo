package com.sparta.kurlyo.controller;

import com.sparta.kurlyo.dto.GoodsRequestDto;
import com.sparta.kurlyo.dto.ResponseDto;
import com.sparta.kurlyo.dto.Response;
import com.sparta.kurlyo.service.GoodsService;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class GoodsController {

    private final GoodsService goodsService;

    // 상품 상세페이지
    @SecurityRequirements
    @GetMapping("/goods/{goodsId}")
    public ResponseEntity<Response> getDetails(@PathVariable long goodsId) {
        return goodsService.getDetails(goodsId);
    }

    // 상세페이지 상품 수량 조절
    @PostMapping("/goods/amount/{goodsId}")
    public ResponseEntity<Response> updateAmount(@PathVariable("goodsId") long goodsId,
                                                @RequestParam("isPlus") boolean isPlus,
                                         @RequestParam("amount_now") int amount_now) {
        return goodsService.updateAmount(goodsId, isPlus, amount_now);
    }

    //카테고리 전체 리스트
    @SecurityRequirements
    @GetMapping("/categories")
    public ResponseEntity<Response> getCategoriesList() {
        return goodsService.getCategoriesList();
    }

    //카테고리 구분 리스트
    @SecurityRequirements
    @GetMapping("/categories/{categoryName}")
    public ResponseEntity<Response> getCategoriesList(@PathVariable String categoryName) {
        return goodsService.getSummaryList(categoryName);
    }

    @PostMapping("/goods2")
    public ResponseDto<Boolean> createGoods2(@RequestBody GoodsRequestDto goodsRequestDto) {
        return goodsService.create2(goodsRequestDto);
    }

    @PostMapping(value = "/goods", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Response> createGoods(@ModelAttribute GoodsRequestDto goodsRequestDto) throws IOException {
        return goodsService.create(goodsRequestDto);
    }
}
