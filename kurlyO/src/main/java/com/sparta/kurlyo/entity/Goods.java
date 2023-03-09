package com.sparta.kurlyo.entity;

import com.sparta.kurlyo.dto.GoodsRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Goods extends TimeStamped{
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(nullable = false)
    private String goodsName;

    @Column
    private Integer count;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private String summary;

    @Column(nullable = true)
    private String image;

    @Column(nullable = false)
//    @Enumerated
    private Packaging packaging;

    @Column
    private String content;

    public Goods(GoodsRequestDto goodsRequestDto, String imageUrl, Category category) {
        this.category = category;
        this.goodsName = goodsRequestDto.getGoodsName();
        this.price = goodsRequestDto.getPrice();
        this.summary = goodsRequestDto.getSummary();
        this.count = goodsRequestDto.getCount();
        this.image = imageUrl;
        this.packaging = Packaging.valueOf(goodsRequestDto.getPackaging());
        this.content = "images";
    }

//    public Goods(GoodsRequestDto goodsRequestDto, Category category){
//        this.category = category;
//        this.goodsName = goodsRequestDto.getGoodsName();
//        this.price = goodsRequestDto.getPrice();
//        this.summary = goodsRequestDto.getSummary();
//        this.count = goodsRequestDto.getCount();
//        this.image = goodsRequestDto.getImage();
//        this.packaging = Packaging.valueOf(goodsRequestDto.getPackaging());
//        this.content = "images";
//        //컨텐츠 이미지 고려
//        //게시자가 관리자이지만 어떤 계정으로 등록하였는지 체크 필요할 듯 상의 후 결정
//    }

//    @Column(nullable = false)
//    private String createdAt;
}
