package com.shoppingproduct.service.cart;


import com.shoppingproduct.exceptions.ResourceNotFoundException;
import com.shoppingproduct.model.Cart;
import com.shoppingproduct.model.CartItem;
import com.shoppingproduct.model.Product;
import com.shoppingproduct.repository.CartItemRepository;
import com.shoppingproduct.repository.CartRepository;
import com.shoppingproduct.service.product.ProductService;
import jakarta.persistence.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service //3:42  4:01
public class CartItemService  implements ICartItemService{

    @Autowired
    ProductService productService;
    @Autowired
    ICartService cartService;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    CartItemRepository cartItemRepository;

    @Override //4:02
    public void addItemToCart(Long cartId, Long productId, int quantity) {
     //1. get the cart
        //2 get the product
        //3 check if the product is allReady  in the cart
        //4. if Yes, then increase the quantity with the requested quantity but if no
        //5.  if No , then initiates  a new cartItem entry

        Cart cart=cartService.getCart(cartId);
        Product product=productService.getProductById(productId);
        CartItem cartItem=cart.getItems().stream()
                .filter(item->item.getProduct().getId().equals(productId))
                .findFirst().orElse(new CartItem());

        if(cartItem.getId()==null)
        {  cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
        }
        else
        {
            cartItem.setQuantity(cartItem.getQuantity()+quantity);
        }

        cartItem.setTotalPrice();
        cart.addItem(cartItem);
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);

    }

    @Override  //4:11
    public void removeItemFromCart(Long cartId, Long productId) {
        Cart cart=cartService.getCart(cartId);
        CartItem cartItemRemove=getCartItem(cartId,productId);
        cart.removeItem(cartItemRemove);
        cartRepository.save(cart);

    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {
      Cart cart=cartService.getCart(cartId);
      cart.getItems().stream().filter(item -> item.getProduct().getId().equals(productId))
              .findFirst().ifPresent(item -> {
                  item.setQuantity(quantity);
                  item.setUnitPrice(item.getProduct().getPrice());
                  item.setTotalPrice();
                      }
              );

        BigDecimal totalAmount=cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        cartRepository.save(cart);
    }

    @Override  //4:20
    public  CartItem getCartItem(Long cartId,Long productId) {
        Cart cart = cartService.getCart(cartId);
        return cart.getItems().stream().filter(item -> item.getProduct().equals(productId))
                .findFirst().orElseThrow(() -> new ResourceNotFoundException("Item not found"));

    }

}
