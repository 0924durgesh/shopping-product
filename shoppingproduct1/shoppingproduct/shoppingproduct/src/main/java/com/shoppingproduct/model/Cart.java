package com.shoppingproduct.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity  //3:40
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private BigDecimal totalAmount=BigDecimal.ZERO;
   // private BigDecimal unitPrice=BigDecimal.ZERO;

    @OneToMany(mappedBy = "cart" ,cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<CartItem> cartItems=new HashSet<>();


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Set<CartItem> getItems() {
        return cartItems;
    }

    public void setItems(Set<CartItem> items) {
        this.cartItems = items;
    }

    public Cart() {
    }

     public void addItem(CartItem item)
     {   this.cartItems.add(item);
         this.setItems(cartItems);
         updateTotalAmount();
     }
     public  void removeItem(CartItem item)
     { this.cartItems.remove(item);
         updateTotalAmount();
     }
// public void UpdateTotalAmount()
// {  this.totalAmount=cartItems.stream().map(item -> {
//        BigDecimal unitPrice=cartItems.g
//
// }).reduce(BigDecimal.ZERO,BigDecimal::add);
//
// }
//public void UpdateTotalAmount() {
//    this.totalAmount = cartItems.stream()
//            .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
//            .reduce(BigDecimal.ZERO, BigDecimal::add);
//}

//    private void updateTotalAmount() {
//        this.totalAmount = cartItems.stream().map(item -> {
//            BigDecimal unitPrice = item.getUnitPrice();
//            if (unitPrice == null) {
//                return BigDecimal.ZERO;
//            }
//            return unitPrice.multiply(BigDecimal.valueOf(item.getQuantity()));
//        }).reduce(BigDecimal.ZERO, BigDecimal::add);
//    }
private void updateTotalAmount() {
    this.totalAmount = cartItems.stream()
            .map(item -> {
                BigDecimal unitPrice = item.getUnitPrice();
                if (unitPrice == null) {
                    return BigDecimal.ZERO;
                }
                return unitPrice.multiply(BigDecimal.valueOf(item.getQuantity()));
            })
            .reduce(BigDecimal.ZERO, BigDecimal::add);
}

}
