package com.example.demo22.Rep;

import com.example.demo22.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> findByIdAccount(int idAccount);
    List<Order> findByStatus(int status);
    List<Order> findByDateBuyBetween(Date startDate, Date endDate);
    List<Order> findByDateBuyBetweenAndStatus(Date startDate, Date endDate, int status);

    List<Order> findByDateRange(@Param("startDate") String startDate, @Param("endDate") String endDate);
}