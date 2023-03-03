package com.sparta.kurlyo.repository;

import com.sparta.kurlyo.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsRepository extends JpaRepository<Goods, Long> {
}
