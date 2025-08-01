package com.shoppingproduct.controller;


import com.shoppingproduct.exceptions.ResourceNotFoundException;
import com.shoppingproduct.model.CartItem;
import com.shoppingproduct.model.Product;
import com.shoppingproduct.service.cart.ICartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/CartItem")
public class CartItemController {

    @Autowired
    ICartItemService cartItemService;
    @PostMapping("/addCartItem")// 4:20
public ResponseEntity<?>  addItemToCart(@RequestParam Long cartId,
                                            @RequestParam Long  productId,
                                            @RequestParam Integer Quantity) {
        try {
            cartItemService.addItemToCart(cartId, productId, Quantity);
            return ResponseEntity.ok("success saved cartItem");

        } catch (ResourceNotFoundException ex) {
            System.out.print(ex.getMessage());
        }
        return (ResponseEntity<?>) ResponseEntity.internalServerError();
    }


    @PutMapping("/updateItemQuantity/{cartId}/{productId}/{quantity}")
    public ResponseEntity<?>  updateItemQuantity(Long cartId,Long productId,int quantity)
    {  try {
        cartItemService.updateItemQuantity(cartId, productId, quantity);
        return ResponseEntity.ok("success saved cartItem");
     }catch (Exception ex)
    {
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed"+ex.getMessage());
    }
    }

@GetMapping("/getCartItem/{cartId}/{productId}")
    public ResponseEntity<?>  getCartItem(Long cartId, Long productId)
    {    try{
        cartItemService.getCartItem(cartId,productId);
          return ResponseEntity.ok("success saved cartItem");
    } catch (Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Failed : " + ex.getMessage());
    }
    }

@DeleteMapping("/deleteCartItem/{cartId}/item/{productId}")
    public ResponseEntity<String> removeItemFromCart(Long cartId, Long productId) {
        try {
            cartItemService.removeItemFromCart(cartId, productId);
            return ResponseEntity.ok("Item removed successfully from cart.");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to remove item: " + ex.getMessage());
        }
    }


}
