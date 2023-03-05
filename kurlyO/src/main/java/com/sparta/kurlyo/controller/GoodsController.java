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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class GoodsController {

    private final GoodsService goodsService;

    @GetMapping("/goods/{goodsId}")
    public ResponseEntity<Response> getDetails(@PathVariable long goodsId) {
        return goodsService.getDetails(goodsId);
    }

    //카테고리 전체 리스트
    @GetMapping("/categories")
    public ResponseDto<List<GoodsListResponseDto>> getCategoriesList() {
        return goodsService.getCategoriesList();
    }
    //카테고리 구분 리스트
    @GetMapping("/categories/{categoryName}")
    public ResponseDto<List<GoodsListResponseDto>> getCategoriesList(@PathVariable String categoryName) {
        return goodsService.getSummaryList(categoryName);
    }
//    @Secured(value = UserRoleEnum.ADMIN) 권한 접근
    @PostMapping("/goods")
    public ResponseDto<Boolean> createGoods(@RequestPart GoodsRequestDto goodsRequestDto, @RequestPart(value = "image") MultipartFile multipartFile) throws IOException {
        return goodsService.create(goodsRequestDto, multipartFile);
    }
}
