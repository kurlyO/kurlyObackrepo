package com.sparta.kurlyo.controller;

import com.sparta.kurlyo.dto.GoodsListResponseDto;
import com.sparta.kurlyo.dto.GoodsRequestDto;
import com.sparta.kurlyo.dto.ResponseDto;
import com.sparta.kurlyo.dto.Response;
import com.sparta.kurlyo.entity.Category;
import com.sparta.kurlyo.entity.Goods;
import com.sparta.kurlyo.service.GoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class GoodsController {

    private final GoodsService goodsService;

    // 상품 상세페이지
    @GetMapping("/goods/{goodsId}")
    public ResponseEntity<Response> getDetails(@PathVariable long goodsId) {
        return goodsService.getDetails(goodsId);
    }

    // 상세페이지 상품 수량 조절
    @PostMapping("/goods/amount")
    public ResponseEntity<Response> updateAmount(@RequestParam("goodsId") long goodsId,
                                              @RequestParam("isPlus") boolean isPlus,
                                              @RequestParam("amount_now") int amount_now) {
        return goodsService.updateAmount(goodsId, isPlus, amount_now);
    }

    //카테고리 전체 리스트
    @GetMapping("/categories")
    public ResponseEntity<Response> getCategoriesList() {
        return goodsService.getCategoriesList();
    }
    //카테고리 구분 리스트
    @GetMapping("/categories/{categoryName}")
    public ResponseEntity<Response> getCategoriesList(@PathVariable String categoryName) {
        return goodsService.getSummaryList(categoryName);
    }
//    @Secured(value = UserRoleEnum.ADMIN) 권한 접근
    @PostMapping(value = "/goods", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Response> createGoods(@ModelAttribute GoodsRequestDto goodsRequestDto) throws IOException {
        return goodsService.create(goodsRequestDto);
    }
}
