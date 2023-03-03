package com.sparta.kurlyo.service;

import com.sparta.kurlyo.entity.Cart;
import com.sparta.kurlyo.entity.Members;
import com.sparta.kurlyo.repository.CartRepository;
import com.sparta.kurlyo.repository.MembersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {

    private CartRepository cartRepository;
    private final MembersRepository membersRepository;

    public void addCart(long goodsId, String username) {
        Members member = getMember(username);
        Optional<Cart> cart = cartRepository.findByGoodsIdAnd
    }

    private Members getMember(String username) {
        return membersRepository.findByName(username);
    }
}
