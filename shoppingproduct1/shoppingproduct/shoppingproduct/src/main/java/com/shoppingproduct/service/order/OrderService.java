package com.shoppingproduct.service.order;


import com.shoppingproduct.dtos.orderDto;
import com.shoppingproduct.enums.OrderStatus;
import com.shoppingproduct.exceptions.ResourceNotFoundException;
import com.shoppingproduct.model.Cart;
import com.shoppingproduct.model.Order;
import com.shoppingproduct.model.OrderItem;
import com.shoppingproduct.model.Product;
import com.shoppingproduct.repository.OrderRepository;
import com.shoppingproduct.repository.ProductRepository;
import com.shoppingproduct.service.cart.ICartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;


@Service //
public class OrderService implements IOrderService{
    @Autowired
    private ProductRepository productRepository;
     @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ICartService cartService;
     @Autowired
    private ModelMapper modelMapper;


    @Transactional
    @Override  //5:40
    public Order placeOrder(Long userId) {
        Cart cart=cartService.getCartByUserId(userId);
         Order order=createOrder(cart);
         List<OrderItem> orderItemList=createOrderItems(order,cart);
         order.setOrderItems(new HashSet<>(orderItemList));
         order.setTotalAmount(calculateTotalAmount(orderItemList));
         Order savedOrder=orderRepository.save(order);
         cartService.clearCart(cart.getId());
        return savedOrder;
    }

    @Override
    public List<Order> getUserOrder(Long userId)
    {   return orderRepository.findByUserId(userId);

    }

    @Override
    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(()->new ResourceNotFoundException("order not found"));
    }

    //5:28

    private Order createOrder(Cart cart)
    { Order order=new Order();
      //  order.setUser(cart.getUser);
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        return order;
    }

    //5:32
    private  List<OrderItem> createOrderItems(Order order, Cart cart)
    {
         return  cart.getItems().stream().map(cartItem->{
         Product product=cartItem.getProduct();
         product.setInventory(product.getInventory()-cartItem.getQuantity());
         productRepository.save(product);
         return new OrderItem(order,product,cartItem.getQuantity(),cartItem.getUnitPrice());
     }).toList();
    }

    //5:27
    private BigDecimal calculateTotalAmount(List<OrderItem> orderItemList)
    {
        return orderItemList.stream().map(item->item.getPrice().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO,BigDecimal::add);

    }

    private orderDto convertToDto(Order order)
    {
        return  modelMapper.map(order,orderDto.class);
    }

}
