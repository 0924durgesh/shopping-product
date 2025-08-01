package com.shoppingproduct.service.cart;

import com.shoppingproduct.model.Cart;

import java.math.BigDecimal;
import java.util.List;

//time 3:48
public interface ICartService {
   Cart getCart(Long id);
    void clearCart(Long id);
  BigDecimal getTotalPrice(Long id);
   Cart addCart(Cart cart);

    Cart getCartByUserId(Long userId);


}
