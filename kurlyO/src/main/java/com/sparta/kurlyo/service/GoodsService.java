package com.sparta.kurlyo.service;

import com.sparta.kurlyo.dto.GoodsListResponseDto;
import com.sparta.kurlyo.dto.GoodsRequestDto;
import com.sparta.kurlyo.dto.GoodsResponseDto;
import com.sparta.kurlyo.dto.ResponseDto;
import com.sparta.kurlyo.entity.Goods;
import com.sparta.kurlyo.entity.Members;
import com.sparta.kurlyo.repository.GoodsRepository;
import com.sparta.kurlyo.s3.S3Uploader;
import lombok.RequiredArgsConstructor;
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

@Service
@RequiredArgsConstructor
public class GoodsService {
    private final GoodsRepository goodsRepository;
    private final S3Uploader s3Uploader;


    @Transactional(readOnly = true)
    public GoodsResponseDto getDetails(long goodsId) {
        return new GoodsResponseDto(getGoods(goodsId));
    }

    private Goods getGoods(long goodsId) {
        return goodsRepository.findById(goodsId).orElseThrow(
                () -> new IllegalArgumentException("해당 상품이 존재하지 않습니다.")
        );
    }

    @Transactional(readOnly = true)
    public ResponseDto<List<GoodsListResponseDto>> getCategoriesList() {
//    public ResponseDto<List<GoodsListResponseDto>> getCategoriesList(int page, int size, String sortBy) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
//        Sort sort = Sort.by(Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(1, 99, sort);
        Page<Goods> goodsPage = goodsRepository.findAll(pageable);
        List<Goods> goods = goodsPage.getContent();
        List<GoodsListResponseDto> goodsList = new ArrayList<>();
        for (Goods goodsGet : goods) {
            goodsList.add(GoodsListResponseDto.of(goodsGet));
        }
        return ResponseDto.success(goodsList);
    }
    @Transactional
    public ResponseDto<Boolean> create(GoodsRequestDto goodsRequestDto, MultipartFile multipartFile) throws IOException {
        String imageUrl = s3Uploader.uploadFiles(multipartFile, "images");
        goodsRepository.save(new Goods(goodsRequestDto, imageUrl));
        return ResponseDto.success(null);
    }
}
