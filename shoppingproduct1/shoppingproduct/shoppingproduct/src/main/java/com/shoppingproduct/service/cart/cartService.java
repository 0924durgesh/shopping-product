package com.shoppingproduct.service.cart;


import com.shoppingproduct.exceptions.ResourceNotFoundException;
import com.shoppingproduct.model.Cart;
import com.shoppingproduct.model.CartItem;
import com.shoppingproduct.repository.CartItemRepository;
import com.shoppingproduct.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;


@Service
public class cartService implements  ICartService{

  @Autowired
  public CartRepository cartRepository;
//    @Autowired
//    private AtomicLong cartIdGenerator=new AtomicLong(0);
  @Autowired
  public CartItemRepository cartItemRepository;
    @Override  //3:52
    public  Cart getCart(Long id) {
        Cart cart= cartRepository.findById(id).
                orElseThrow(()->new ResourceNotFoundException("Cart not found"));

        BigDecimal totalAmount= cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        return cartRepository.save(cart);
    }

    @Override//3:53
    public void clearCart(Long id) {
        Cart cart=getCart(id);
        cartRepository.deleteAllById(id);
        cart.getItems().clear();
        cartRepository.deleteById(id);

    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
      Cart cart=getCart(id);
      return cart.getItems().stream().map(CartItem::getTotalPrice)
              .reduce(BigDecimal.ZERO,BigDecimal::add);
    }

    @Override
    public Cart addCart(Cart cart) {
       Cart SaveCart= cartRepository.save(cart);
        return SaveCart;
    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

//    public Long initializeNewCart()
//    {  Cart newCart=new Cart();
//        Long newCartId=cartIdGenerator.incrementAndGet();
//        newCart.setId(newCartId);
//        return  cartRepository.save(newCart).getId();
//
//    }
}
