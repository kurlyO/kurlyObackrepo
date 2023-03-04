package com.sparta.kurlyo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Goods extends TimeStamped {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(nullable = false)
    private String goodsName;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private String summary;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private Packaging packaging;

    @Column
    private String content;
}
