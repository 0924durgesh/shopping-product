package com.shoppingproduct.controller;


import com.shoppingproduct.model.Order;
import com.shoppingproduct.response.ApiResponse;
import com.shoppingproduct.service.order.IOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/orders")//6:00
public class OrderController {
    private IOrderService orderService;


//
    @PostMapping("/placeOrder")
    public ResponseEntity<Order> placeOrder(@PathVariable Long id)
    {Order order=null;
        try{
             order=orderService.placeOrder(id);
        }catch (Exception e)
        {
          //return (ResponseEntity<Order>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
          //return (ResponseEntity<Order>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error Occur",e.getMassage()));
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok().body(order);
    }

  @GetMapping("/getOrderByOrderId/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable  Long orderId) {
      try{
        Order order = orderService.getOrder(orderId);
         return ResponseEntity.ok().body(order);
     //return  ResponseEntity.ok(new ApiResponse("Item Order Success",order));
      }
     catch (Exception e) {
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
         //return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>("something internal error",e.getMessage()));        }
         }
    }

    @GetMapping("/getOrderByUserId/{userId}")
    public   ResponseEntity<List<Order>> getUserOrder(@PathVariable Long userId)
    {   try {
        List<Order> order = orderService.getUserOrder(userId);
        return ResponseEntity.ok().body(order);
    }catch (Exception e)
    {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    }



}
