package com.sparta.kurlyo.repository;

import com.sparta.kurlyo.entity.Category;
import com.sparta.kurlyo.entity.Goods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GoodsRepository extends JpaRepository<Goods, Long> {
    Page<Goods> findAllByCategoryName(String categoryName, Pageable pageable);
}
