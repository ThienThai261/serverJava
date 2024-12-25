package com.example.demo22.Model;

import java.io.Serializable;
import java.util.Objects;

public class OrderDetailId implements Serializable {
    private String idOrder;
    private String idProduct;

    public OrderDetailId() {}

    public OrderDetailId(String idOrder, String idProduct) {
        this.idOrder = idOrder;
        this.idProduct = idProduct;
    }

    public String getIdOrder() { return idOrder; }
    public void setIdOrder(String idOrder) { this.idOrder = idOrder; }

    public String getIdProduct() { return idProduct; }
    public void setIdProduct(String idProduct) { this.idProduct = idProduct; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDetailId that = (OrderDetailId) o;
        return Objects.equals(idOrder, that.idOrder) &&
                Objects.equals(idProduct, that.idProduct);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idOrder, idProduct);
    }
}