package com.sparta.kurlyo.service;

import com.sparta.kurlyo.dto.*;
import com.sparta.kurlyo.entity.Cart;
import com.sparta.kurlyo.entity.Goods;
import com.sparta.kurlyo.entity.Members;
import com.sparta.kurlyo.repository.CartRepository;
import com.sparta.kurlyo.repository.GoodsRepository;
import com.sparta.kurlyo.repository.MembersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.sparta.kurlyo.dto.ExceptionMessage.*;

import static com.sparta.kurlyo.dto.SuccessMessage.*;


@Slf4j
@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final MembersRepository membersRepository;
    private final GoodsRepository goodsRepository;

    @Transactional
    public ResponseEntity<Response> addCart(long goodsId, int amount, String username) {
        Members member = getMember(username);
        Optional<Cart> cart = cartRepository.findByGoods_IdAndMembers_Account(goodsId, username);
        Goods goods = getGoods(goodsId);

        if (cart.isPresent()) {
            int remain = goods.getCount() - cart.get().getAmount();
            if (checkAmount(amount, remain)) {
                cart.get().updateAmount(amount);
            } else {
                return Response.toAllExceptionResponseEntity(AMOUNT_OVER_COUNT, new AmountResponseDto(remain));
            }
        } else {
            int remain = goods.getCount();
            if (checkAmount(amount, remain)) {
                cartRepository.save(new Cart(member, goods, amount));
            } else {
                return Response.toAllExceptionResponseEntity(AMOUNT_OVER_COUNT, new AmountResponseDto(remain));
            }
        }

        return Response.toResponseEntity(ADD_CART_SUCCESS);
    }

    private boolean checkAmount(int amount, int remain) {
        if (amount <= remain) {
            return true;
        }
        return false;
    }

    private Goods getGoods(long goodsId) {
        return goodsRepository.findById(goodsId).orElseThrow(
                () -> new CustomException(GOODS_NOT_FOUND)
        );
    }

    private Members getMember(String username) {
        return membersRepository.findByAccount(username).orElseThrow(
                () -> new CustomException(UNAUTHORIZED_MEMBER)
        );
    }

    @Transactional(readOnly = true)
    public ResponseEntity<CartWholeResponseDto> getCart(Members member) {
        // 장바구니 목록을 가져오는 것
        CartWholeResponseDto dto = new CartWholeResponseDto();
        // 특정 사용자의 장바구니 목록을 가지고 옴
        List<Cart> cartList = cartRepository.findCartsByMembers_Id(member.getId());
        String test = "test";
        for (Cart cart : cartList) {
            dto.addGoodsCart(cart);
        }
        return ResponseEntity.ok(dto);
    }

    @Transactional
    public ResponseEntity<Response> updateGoodsCart
            (Long cartId,
             CartRequestDto requestDto,
             Members member) {

        // 기능
        // 카트의 상품을 추가하기 위해서
        // isPlus == true : 카트의 상품 수량이 증가한다.
        // isPlust == false : 카트의 상품 수량이 감소한다.


        Cart cart = cartRepository.findById(cartId).orElseThrow(
                () -> new IllegalArgumentException("해당 장바구니가 존재하지 않습니다.")
        );

        // 사용자의 장바구니 확인
        if (member.getId() != cart.getMembers().getId()) {
            throw new AccessDeniedException("권한이 없습니다.");
        }

        // isPlus == true : cart.amount += 1
        // isPlus == false : cart.amount -= 1
        if (requestDto.getIsPlus()) {
            cart.updateAmount(1);
        } else {
            cart.updateAmount(-1);
        }

        if (cart.getAmount() <= 0) {
            throw new CustomException(AMOUNT_UNDER_COUNT);
        }
        if (cart.getGoods().getCount() < cart.getAmount()) {
            return Response.toAllExceptionResponseEntity(AMOUNT_OVER_COUNT, new AmountResponseDto(cart.getGoods().getCount()));
        }

        return Response.toResponseEntity(ADD_CART_SUCCESS, CartResponseDto.of(cart));
    }

    //오류 문제 객체끼리 비교하셨습니다 객체 == 객체
    @Transactional
    public ResponseEntity<Response> deleteGoodsCart
    (Long cartId,
     Members member) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(
                () -> new CustomException(CART_NOT_FOUND)
        );

        if (!member.getMemberName().equals(cart.getMembers().getMemberName())) {
            throw new CustomException(CART_GOODS_DELETE_FORBIDDEN);
        }

        cartRepository.delete(cart);
        return Response.toResponseEntity(DELETE_CART_GOODS_SUCCESS);
    }

    @Transactional
    public ResponseEntity<Response> BuyGoodsCart(CartBoughtRequestDto cartIdList, Members member) {

        for (Long cartId : cartIdList.getCartIdList()) {
            Cart cart = cartRepository.findById(cartId).orElseThrow(
                    () -> new CustomException(CART_NOT_FOUND)
            );
            Goods goods = goodsRepository.findById(cart.getGoods().getId()).orElseThrow(
                    () -> new CustomException(GOODS_NOT_FOUND)
            );
            if (!cart.getMembers().getAccount().equals(member.getAccount())) {
                throw new CustomException(CANNOT_CART_GOODS_BUY);
            }
            if (!(cart.getAmount() <= goods.getCount())) {
                throw new BoughtException(GOODS_COUNT_INVALID_RANGE, "상품 :" + goods.getGoodsName() + "의 최대 수량은 " + goods.getCount() + " 입니다.");
            }
            goodsRepository.updateGoodsCount(goods.getId(), goods.getCount() - cart.getAmount());
            cartRepository.delete(cart);
        }
        return Response.toResponseEntity(BUY_SUCCESS);
    }
}