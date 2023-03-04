package com.sparta.kurlyo.repository;

import com.sparta.kurlyo.entity.Cart;
import com.sparta.kurlyo.entity.Members;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
//    Optional<Cart> findByGoods_IdAndMembers_Account(Long id, String account);

    List<Cart> findByMember(Members members);
}
