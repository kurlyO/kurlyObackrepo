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
                return Response.toAllExceptionResponseEntity(AMOUNT_OVER_COUNT, new CountResponseDto(remain));
            }
        } else {
            int remain = goods.getCount();
            if (checkAmount(amount, remain)) {
                cartRepository.save(new Cart(member, goods, amount));
            } else {
                return Response.toAllExceptionResponseEntity(AMOUNT_OVER_COUNT, new CountResponseDto(remain));
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
    public ResponseEntity<Response> getCart(Members member) {
        // ???????????? ????????? ???????????? ???
        CartWholeResponseDto dto = new CartWholeResponseDto();
        // ?????? ???????????? ???????????? ????????? ????????? ???
        List<Cart> cartList = cartRepository.findCartsByMembers_Id(member.getId());
        String test = "test";
        for (Cart cart : cartList) {
            dto.addGoodsCart(cart);
        }
        return Response.toResponseEntity(CART_LIST_SUCCESS, dto);
    }

    @Transactional
    public ResponseEntity<Response> updateGoodsCart
            (Long cartId,
             CartRequestDto requestDto,
             Members member) {

        // ??????
        // ????????? ????????? ???????????? ?????????
        // isPlus == true : ????????? ?????? ????????? ????????????.
        // isPlust == false : ????????? ?????? ????????? ????????????.


        Cart cart = cartRepository.findById(cartId).orElseThrow(
                () -> new IllegalArgumentException("?????? ??????????????? ???????????? ????????????.")
        );

        // ???????????? ???????????? ??????
        if (member.getId() != cart.getMembers().getId()) {
            throw new AccessDeniedException("????????? ????????????.");
        }

        // isPlus == true : cart.amount += 1
        // isPlus == false : cart.amount -= 1
        if (requestDto.getIsPlus()) {
            if(cart.getGoods().getCount() <= cart.getAmount()) {
                return Response.toAllExceptionResponseEntity(AMOUNT_OVER_COUNT, new CountResponseDto(cart.getGoods().getCount()));
            }
            cart.updateAmount(1);
        } else {
            if(cart.getAmount() <= 1) {
                throw new CustomException(AMOUNT_UNDER_COUNT);
            }
            cart.updateAmount(-1);
        }
        return Response.toResponseEntity(UPDATE_CART_SUCCESS, CartResponseDto.of(cart));

    }

    //?????? ?????? ???????????? ????????????????????? ?????? == ??????
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
                throw new BoughtException(GOODS_COUNT_INVALID_RANGE, "?????? :" + goods.getGoodsName() + "??? ?????? ????????? " + goods.getCount() + " ?????????.");
            }
            goodsRepository.updateGoodsCount(goods.getId(), goods.getCount() - cart.getAmount());
            cartRepository.delete(cart);
        }
        return Response.toResponseEntity(BUY_SUCCESS);
    }
}