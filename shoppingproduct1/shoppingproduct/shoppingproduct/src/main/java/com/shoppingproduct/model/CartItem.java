package com.shoppingproduct.model;


import jakarta.persistence.*;

import javax.annotation.processing.Generated;
import java.math.BigDecimal;

@Entity // time:--3:30
public class CartItem {
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Long id;

  private  int quantity;
  private BigDecimal unitPrice;
  private BigDecimal totalPrice;
@ManyToOne  //3:43
@JoinColumn(name="product_id")
  private Product product;
  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name="card_id")
  private Cart cart;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public BigDecimal getUnitPrice() {
    return unitPrice;
  }

  public void setUnitPrice(BigDecimal unitPrice) {
    this.unitPrice = unitPrice;
  }

  public BigDecimal getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(BigDecimal totalPrice) {
    this.totalPrice = totalPrice;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public Cart getCart() {
    return cart;
  }

  public void setCart(Cart cart) {
    this.cart = cart;
  }

  //3:46
  public  void  setTotalPrice()
  {
    this.totalPrice=this.unitPrice.multiply(new BigDecimal(quantity));
  }
}
