package com.sparta.kurlyo.controller;

import com.sparta.kurlyo.dto.GoodsListResponseDto;
import com.sparta.kurlyo.dto.GoodsRequestDto;
import com.sparta.kurlyo.dto.GoodsResponseDto;
import com.sparta.kurlyo.dto.ResponseDto;
import com.sparta.kurlyo.security.UserDetailsImpl;
import com.sparta.kurlyo.service.GoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public GoodsResponseDto getDetails(@PathVariable long goodsId) {
        return goodsService.getDetails(goodsId);
    }

    @GetMapping("/categories")
    public ResponseDto<List<GoodsListResponseDto>> getCategoriesList() {
        return goodsService.getCategoriesList();
    }
    @PostMapping("/goods")
    public ResponseDto<Boolean> createGoods(@RequestPart GoodsRequestDto goodsRequestDto, @RequestPart(value = "image") MultipartFile multipartFile, @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        if (!userDetails.isEnabled() &&!userDetails.getMember().getRole().getAuthority().equals("ROLE_ADMIN")){
            return ResponseDto.fail();
        }
        return goodsService.create(goodsRequestDto, multipartFile);
    }
}
