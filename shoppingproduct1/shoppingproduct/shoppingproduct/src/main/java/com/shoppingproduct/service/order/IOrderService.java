package com.shoppingproduct.service.order;


import com.shoppingproduct.model.Order;

import java.util.List;


//5:23

public interface IOrderService {
    Order placeOrder(Long id);

    List<Order> getUserOrder(Long userId);

    Order getOrder(Long orderId);
}
