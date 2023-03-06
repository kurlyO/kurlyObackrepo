package com.sparta.kurlyo.repository;

import com.sparta.kurlyo.entity.Cart;
import com.sparta.kurlyo.entity.Members;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

//    List<Cart> findByMembers(Members members);
    List<Cart> findCartsByMembers_Id(Long members);
    Optional<Cart> findByGoods_IdAndMembers_Account(Long id, String account);

}
