package com.shoppingproduct.controller;


import com.shoppingproduct.exceptions.ResourceNotFoundException;
import com.shoppingproduct.model.Cart;
import com.shoppingproduct.repository.CartRepository;
import com.shoppingproduct.service.cart.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/cart") //4:20 -4:28
public class CartController {

    @Autowired
    private  ICartService cartService;

    @Autowired
    private CartRepository cartRepository;
    @GetMapping("/getCart/{cartId}")
    public ResponseEntity<?> getCart(@PathVariable Long cartId)
    {   try {
        Cart cart = cartService.getCart(cartId);
        return ResponseEntity.ok().body(cart);
    }catch (Exception e)
    {
        return ResponseEntity.status(NOT_FOUND).body(null);
    }
    }

    public  ResponseEntity<?> clearCart(@PathVariable Long cartId)
    {   try {
        cartService.clearCart(cartId);
       return ResponseEntity.ok().body("cart has been clear");
        }
        catch (Exception e){
        System.out.print(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    }


    @GetMapping("/cartTotalAmount/{cartId}")
    public ResponseEntity<?> getAllAmount(@PathVariable Long cartId )
    {  try {


        BigDecimal amount = cartService.getTotalPrice(cartId);
        return ResponseEntity.ok(amount);
    }catch (ResourceNotFoundException Ex)
    {
        return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }
}
