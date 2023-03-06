package com.sparta.kurlyo.service;

import com.sparta.kurlyo.dto.*;
import com.sparta.kurlyo.entity.Category;
import com.sparta.kurlyo.entity.Goods;
import com.sparta.kurlyo.repository.CategoryRepository;
import com.sparta.kurlyo.repository.GoodsRepository;
import com.sparta.kurlyo.s3.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.sparta.kurlyo.dto.ExceptionMessage.*;
import static com.sparta.kurlyo.dto.SuccessMessage.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoodsService {
    private final GoodsRepository goodsRepository;
    private final CategoryRepository categoryRepository;
    private final S3Uploader s3Uploader;

    //상세 페이지
    @Transactional(readOnly = true)
    public ResponseEntity<Response> getDetails(long goodsId) {
        return new Response().toResponseEntity(GOODS_DETAIL_SUCCESS,
                new GoodsResponseDto(getGoods(goodsId)));
    }

    private Goods getGoods(long goodsId) {
        return goodsRepository.findById(goodsId).orElseThrow(
                () -> new CustomException(GOODS_NOT_FOUND)
        );
    }

    //상품 등록 페이지
    @Transactional
    public ResponseEntity<Response> create(GoodsRequestDto goodsRequestDto, MultipartFile multipartFile) throws IOException {
        if (goodsRepository.findByGoodsName(goodsRequestDto.getGoodsName().toString()).isPresent()){
            return new Response().toAllExceptionResponseEntity(DUPLICATE_GOODS, goodsRequestDto.getGoodsName());
        }
        String imageUrl = s3Uploader.uploadFiles(multipartFile, "images");
        Category category = categoryRepository.findByName(goodsRequestDto.getCategory());
        goodsRepository.save(new Goods(goodsRequestDto, imageUrl, category));
        return new Response().toResponseEntity(SuccessMessage.GOODS_POST_SUCCESS);
    }

    //상품 전체 리스트(현재 페이징 지정값 전달 /프론트 진행 상황에 맞춰 변경 예정)
    //작성일자/수정일자 dto 추가해서 넣을지 결정 필요
    @Transactional(readOnly = true)
    public ResponseEntity<Response> getCategoriesList() {
//    public ResponseDto<List<GoodsListResponseDto>> getCategoriesList(int page, int size, String sortBy) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createAt");
//        Sort sort = Sort.by(Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(0, 99, sort);
        Page<Goods> goodsPage = goodsRepository.findAll(pageable);
        List<GoodsListResponseDto> goodsList = new ArrayList<>();
        for (Goods goodsGet : goodsPage) {
            goodsList.add(GoodsListResponseDto.of(goodsGet));
        }
        return new Response().toResponseEntity(GOODS_ALL_CATEGORY_LIST_SUCCESS,
                goodsList);
    }

    @Transactional
    public ResponseEntity<Response> getSummaryList(String categoryName) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createAt");
        Pageable pageable = PageRequest.of(0, 99, sort);
        Page<Goods> goodsCategoryPage = goodsRepository.findAllByCategoryName(categoryName, pageable);
        if (goodsCategoryPage.isEmpty()) {
            throw new CustomException(CARTEGORY_NOT_FOUND);
        }
        List<GoodsListResponseDto> goodsCategoryList = new ArrayList<>();
        for (Goods goods : goodsCategoryPage) {
            goodsCategoryList.add(GoodsListResponseDto.of(goods));
        }
        return new Response().toResponseEntity(GOODS_CATEGORY_LIST_SUCCESS,
                goodsCategoryList);
    }

    @Transactional
    public ResponseEntity<Response> updateAmount(long goodsId, boolean isPlus, int amount_now) {
        Goods goods = getGoods(goodsId);
        int amount = isPlus ? amount_now + 1 : amount_now - 1;
        if (amount > goods.getCount()) {
            return Response.toAllExceptionResponseEntity(AMOUNT_OVER_COUNT, new AmountResponseDto(goods.getCount()));
        }
        if (amount <= 0) {
            return Response.toAllExceptionResponseEntity(AMOUNT_UNDER_COUNT, new AmountResponseDto(1));
        }
        return new Response().toResponseEntity(GOODS_UPDATE_AMOUNT_SUCCESS, new AmountResponseDto(goods.getCount()));
    }
}
