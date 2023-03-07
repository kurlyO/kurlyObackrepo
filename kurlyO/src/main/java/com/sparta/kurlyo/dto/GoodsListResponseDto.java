//package com.sparta.kurlyo.dto;
//
//import com.sparta.kurlyo.entity.Category;
//import com.sparta.kurlyo.entity.Goods;
//import lombok.Builder;
//import lombok.Getter;
//
//import java.util.List;
//
//@Getter
//@Builder
//public class GoodsListResponseDto {
//    private List<GoodsResponseDto> goodsDtoList;
//    private List<CategoryDto> categoryDtoList;
//
//    public static GoodsListResponseDto of(List<GoodsResponseDto> goodsDtoList, List<CategoryDto> categoryDtoList) {
//        return GoodsListResponseDto.builder()
//                .goodsDtoList(goodsDtoList)
//                .categoryDtoList(categoryDtoList)
//                .build();
//    }
//}
