package com.example.demo22.Controller;

import com.example.demo22.Model.Order;
import com.example.demo22.Model.OrderDetail;
import com.example.demo22.Rep.OrderRepository;
import com.example.demo22.Rep.OrderDetailRepository;
import com.example.demo22.Rep.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/analytics")
@CrossOrigin(origins = "*")
public class AnalyticsController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/sales/summary")
    public ResponseEntity<?> getSalesSummary(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        try {
            List<Order> orders = orderRepository.findByDateBuyBetweenAndStatus(startDate, endDate, 4); // Status 4 for completed orders

            // Calculate daily sales
            Map<Date, Double> dailySales = orders.stream()
                    .collect(Collectors.groupingBy(
                            Order::getDateBuy,
                            Collectors.summingDouble(order -> order.getOrderDetails().stream()
                                    .mapToDouble(detail -> detail.getPrice() * detail.getQuantity())
                                    .sum())
                    ));

            // Calculate top products
            Map<String, Integer> productSales = orders.stream()
                    .flatMap(order -> order.getOrderDetails().stream())
                    .collect(Collectors.groupingBy(
                            OrderDetail::getIdProduct,
                            Collectors.summingInt(OrderDetail::getQuantity)
                    ));

            // Calculate total revenue
            double totalRevenue = dailySales.values().stream().mapToDouble(Double::doubleValue).sum();

            // Calculate total orders
            int totalOrders = orders.size();

            // Calculate average order value
            double averageOrderValue = totalRevenue / totalOrders;

            Map<String, Object> summary = new HashMap<>();
            summary.put("dailySales", dailySales);
            summary.put("topProducts", productSales);
            summary.put("totalRevenue", totalRevenue);
            summary.put("totalOrders", totalOrders);
            summary.put("averageOrderValue", averageOrderValue);

            return ResponseEntity.ok(summary);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error generating analytics: " + e.getMessage());
        }
    }

    @GetMapping("/export/csv")
    public ResponseEntity<?> exportSalesData(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        try {
            List<Order> orders = orderRepository.findByDateBuyBetween(startDate, endDate);
            StringBuilder csv = new StringBuilder();
            csv.append("Order ID,Date,Customer ID,Total Amount,Status\n");

            for (Order order : orders) {
                double totalAmount = order.getOrderDetails().stream()
                        .mapToDouble(detail -> detail.getPrice() * detail.getQuantity())
                        .sum();

                csv.append(String.format("%s,%s,%d,%.2f,%d\n",
                        order.getId(),
                        order.getDateBuy(),
                        order.getIdAccount(),
                        totalAmount,
                        order.getStatus()));
            }

            return ResponseEntity.ok()
                    .header("Content-Type", "text/csv")
                    .header("Content-Disposition", "attachment; filename=sales_report.csv")
                    .body(csv.toString());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error exporting data: " + e.getMessage());
        }
    }
}