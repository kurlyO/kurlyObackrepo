package com.sparta.kurlyo.service;

import com.sparta.kurlyo.dto.GoodsResponseDto;
import com.sparta.kurlyo.dto.ResponseDto;
import com.sparta.kurlyo.entity.Goods;
import com.sparta.kurlyo.repository.GoodsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GoodsService {
    private final GoodsRepository goodsRepository;

    @Transactional(readOnly = true)
    public GoodsResponseDto getDetails(long goodsId) {
        return new GoodsResponseDto(getGoods(goodsId));
    }

    private Goods getGoods(long goodsId) {
        return goodsRepository.findById(goodsId).orElseThrow(
                () -> new IllegalArgumentException("해당 상품이 존재하지 않습니다.")
        );
    }

    @Transactional
    public ResponseDto<List<GoodsResponseDto>> getCategoriesList(int page, int size, String sortBy) {
        Sort sort = Sort.by(Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Goods> goodsPage = goodsRepository.findAll(pageable);
        List<Goods> goods = goodsPage.getContent();
        List<GoodsResponseDto> goodsList = new ArrayList<>();
        for(Goods goodsSet : goods){

        }
        return ResponseDto.success();
    }
}
