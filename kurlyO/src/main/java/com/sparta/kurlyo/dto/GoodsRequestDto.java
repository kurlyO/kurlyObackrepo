package com.sparta.kurlyo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class GoodsRequestDto {
    private String goodsName;
    private Integer price;
    private String summary;
    private String image;
    private String category;
    private String packaging;
    private Integer count;

    private MultipartFile multipartFile;
}
