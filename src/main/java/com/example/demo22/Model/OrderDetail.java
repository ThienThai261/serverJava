package com.example.demo22.Model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "order_details")
@IdClass(OrderDetailId.class)
public class OrderDetail {
    @Id
    @Column(name = "idOrder")
    private String idOrder;

    @Id
    @Column(name = "idProduct")
    private String idProduct;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "price", nullable = false)
    private int price;

    @ManyToOne
    @JoinColumn(name = "idOrder", insertable = false, updatable = false)
    @JsonIgnore
    private Order order;

    // Getters and Setters
    public String getIdOrder() { return idOrder; }
    public void setIdOrder(String idOrder) { this.idOrder = idOrder; }

    public String getIdProduct() { return idProduct; }
    public void setIdProduct(String idProduct) { this.idProduct = idProduct; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }

    public Order getOrder() { return order; }
    public void setOrder(Order order) {
        this.order = order;
        this.idOrder = order != null ? order.getId() : null;
    }
}