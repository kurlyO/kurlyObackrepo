package com.sparta.kurlyo.dto;

import com.sparta.kurlyo.entity.Category;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryDto {
    private Long categoryId;
    private String name;

    public static CategoryDto of(Category category){
        return CategoryDto.builder()
                .categoryId(category.getId())
                .name(category.getName())
                .build();
    }
}
