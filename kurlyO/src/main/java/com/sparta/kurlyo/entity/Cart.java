package com.sparta.kurlyo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Cart extends TimeStamped {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "members_id", nullable = false)
    private Members members;

    @ManyToOne(fetch = FetchType.EAGER) //N+1 발생 처리해야함
    @JoinColumn(name = "goods_id", nullable = false)
    private Goods goods;

    public Cart(Members members, Goods goods, int amount) {
        this.members = members;
        this.goods = goods;
        this.amount = amount;
    }

    public void updateAmount(int amount) {
        this.amount += amount;
    }
}
