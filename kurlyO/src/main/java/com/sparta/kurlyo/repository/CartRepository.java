package com.sparta.kurlyo.repository;

import com.sparta.kurlyo.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByGoods_IdAndMember_Account(Long id, String account);

}
