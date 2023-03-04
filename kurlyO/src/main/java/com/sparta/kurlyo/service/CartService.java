package com.sparta.kurlyo.service;

import com.sparta.kurlyo.dto.CustomException;
import com.sparta.kurlyo.dto.ExceptionMessage;
import com.sparta.kurlyo.dto.Response;
import com.sparta.kurlyo.dto.SuccessMessage;
import com.sparta.kurlyo.entity.Cart;
import com.sparta.kurlyo.entity.Goods;
import com.sparta.kurlyo.entity.Members;
import com.sparta.kurlyo.repository.CartRepository;
import com.sparta.kurlyo.repository.GoodsRepository;
import com.sparta.kurlyo.repository.MembersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final MembersRepository membersRepository;
    private final GoodsRepository goodsRepository;

    @Transactional
    public ResponseEntity<Response> addCart(long goodsId, String username) {
        Members member = getMember(username);
        Optional<Cart> cart = cartRepository.findByGoods_IdAndMember_Account(goodsId, username);
        if (cart.isPresent()) {
            cart.get().addAmount();
        } else {
            Goods goods = getGoods(goodsId);
            cartRepository.save(new Cart(member, goods));
        }
        return Response.toResponseEntity(SuccessMessage.ADD_CART_SUCCESS);
    }

    private Goods getGoods(long goodsId) {
        return goodsRepository.findById(goodsId).orElseThrow(
                () -> new CustomException(ExceptionMessage.GOODS_NOT_FOUND)
        );
    }

    private Members getMember(String username) {
        return membersRepository.findByAccount(username).orElseThrow(
                () -> new CustomException(ExceptionMessage.UNAUTHORIZED_MEMBER)
        );
    }
}
