package com.sparta.kurlyo.service;

import com.sparta.kurlyo.dto.GoodsListResponseDto;
import com.sparta.kurlyo.dto.GoodsRequestDto;
import com.sparta.kurlyo.dto.GoodsResponseDto;
import com.sparta.kurlyo.dto.Response;
import com.sparta.kurlyo.dto.SuccessMessage;
import com.sparta.kurlyo.dto.ResponseDto;
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
        return new Response().toResponseEntity(SuccessMessage.GOODS_DETAIL_SUCCESS,
                new GoodsResponseDto(getGoods(goodsId)));
    }

    private Goods getGoods(long goodsId) {
        return goodsRepository.findById(goodsId).orElseThrow(
                () -> new IllegalArgumentException("해당 상품이 존재하지 않습니다.")
        );
    }
    //상품 등록 페이지
    @Transactional
    public ResponseDto<Boolean> create(GoodsRequestDto goodsRequestDto, MultipartFile multipartFile) throws IOException {
        String imageUrl = s3Uploader.uploadFiles(multipartFile, "images");
        Category category = categoryRepository.findByName(goodsRequestDto.getCategory());
        goodsRepository.save(new Goods(goodsRequestDto, imageUrl, category));
        return ResponseDto.success(null);
    }

    //상품 전체 리스트(현재 페이징 지정값 전달 /프론트 진행 상황에 맞춰 변경 예정)
    @Transactional(readOnly = true)
    public ResponseDto<List<GoodsListResponseDto>> getCategoriesList() {
//    public ResponseDto<List<GoodsListResponseDto>> getCategoriesList(int page, int size, String sortBy) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createAt");
//        Sort sort = Sort.by(Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(0, 99, sort);
        Page<Goods> goodsPage = goodsRepository.findAll(pageable);
        List<GoodsListResponseDto> goodsList = new ArrayList<>();
        for (Goods goodsGet : goodsPage) {
            goodsList.add(GoodsListResponseDto.of(goodsGet));
        }
        return ResponseDto.success(goodsList);
    }

    @Transactional
    public ResponseDto<List<GoodsListResponseDto>> getSummaryList(String categoryName) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createAt");
        Pageable pageable = PageRequest.of(0, 99, sort);
        Page<Goods> goodsCategoryPage = goodsRepository.findAllByCategoryName(categoryName, pageable); //Exception 여부 체크 필요
        if (goodsCategoryPage.isEmpty()){
            log.info("카테고리 항목이 없을 경우 Exception 발생");
        }
        List<GoodsListResponseDto> goodsCategoryList = new ArrayList<>();
        for (Goods goods : goodsCategoryPage) {
            goodsCategoryList.add(GoodsListResponseDto.of(goods));
        }
        return ResponseDto.success(goodsCategoryList);
    }
}
