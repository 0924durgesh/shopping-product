package com.shoppingproduct.repository;

import com.shoppingproduct.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
  List<Order> findByUserId(Long userId);
}
