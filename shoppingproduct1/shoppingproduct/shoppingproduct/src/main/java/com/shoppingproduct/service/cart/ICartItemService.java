package com.shoppingproduct.service.cart;


import com.shoppingproduct.model.CartItem;

public interface ICartItemService {  // working start -3:59

    void addItemToCart(Long cardId,Long productId,int quantity);
    void removeItemFromCart(Long cartId,Long productId);
    void updateItemQuantity(Long cartId,Long productId,int quantity);

    CartItem getCartItem(Long cartId, Long productId);
}
