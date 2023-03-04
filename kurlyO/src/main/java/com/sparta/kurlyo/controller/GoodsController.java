package com.sparta.kurlyo.controller;

import com.sparta.kurlyo.dto.Response;
import com.sparta.kurlyo.service.GoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class GoodsController {

    private final GoodsService goodsService;

    @GetMapping("/goods/{goodsId}")
    public ResponseEntity<Response> getDetails(@PathVariable long goodsId) {
        return goodsService.getDetails(goodsId);
    }
}
