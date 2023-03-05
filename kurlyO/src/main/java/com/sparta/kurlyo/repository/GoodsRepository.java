package com.sparta.kurlyo.repository;

import com.sparta.kurlyo.entity.Category;
import com.sparta.kurlyo.entity.Goods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface GoodsRepository extends JpaRepository<Goods, Long> {
    Page<Goods> findAllByCategoryName(String categoryName, Pageable pageable);
    @Modifying
    @Query("UPDATE Goods p SET p.count = :count WHERE p.id = :goodsId")
    void updateGoodsCount(@Param("goodsId") Long goodsId, @Param("count") Integer count);
}
