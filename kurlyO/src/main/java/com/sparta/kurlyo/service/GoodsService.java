package com.sparta.kurlyo.service;

import com.sparta.kurlyo.dto.GoodsResponseDto;
import com.sparta.kurlyo.dto.Response;
import com.sparta.kurlyo.dto.SuccessMessage;
import com.sparta.kurlyo.entity.Goods;
import com.sparta.kurlyo.repository.GoodsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GoodsService {
    private final GoodsRepository goodsRepository;

    @Transactional(readOnly = true)
    public ResponseEntity<Response> getDetails(long goodsId) {
        return new Response().toResponseEntity(SuccessMessage.GOODS_DETAIL_SUCCESS,
                new GoodsResponseDto(getGoods(goodsId)));
    }

    private Goods getGoods(long goodsId) {
        return goodsRepository.findById(goodsId).orElseThrow(
                () -> new IllegalArgumentException("해당 상품이 존재하지 않습니다.")
        );
    }
}
