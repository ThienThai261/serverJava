package com.example.demo22.Rep;

import com.example.demo22.Model.OrderDetail;
import com.example.demo22.Model.OrderDetailId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailId> {
    List<OrderDetail> findByIdOrder(String idOrder);
}