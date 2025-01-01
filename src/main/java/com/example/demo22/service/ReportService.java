package com.example.demo22.service;

import com.example.demo22.Model.InventoryReportDTO;
import com.example.demo22.Model.Order;
import com.example.demo22.Model.SalesReportDTO;
import com.example.demo22.Model.UserActivityReportDTO;
import com.example.demo22.Rep.OrderRepository;
import com.example.demo22.Rep.ProductRepository;
import com.example.demo22.Rep.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ReportService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ReportService(OrderRepository orderRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

//    public SalesReportDTO getSalesReport(String startDate, String endDate) {
//        // Query doanh thu từ Order và OrderDetail
//        List<Order> orders = orderRepository.findByDateRange(startDate, endDate);
//        double totalRevenue = orders.stream()
//                .flatMap(order -> order.getOrderDetails().stream())
//                .mapToDouble(detail -> detail.getQuantity() * detail.getPrice())
//                .sum();
//
//        return new SalesReportDTO(totalRevenue, startDate, endDate);
//    }

    public List<InventoryReportDTO> getInventoryReport() {
        // Lấy tồn kho từ Product
        return StreamSupport.stream(productRepository.findAll().spliterator(), false)
                .map(product -> new InventoryReportDTO(product.getName(), product.getQuantity()))
                .collect(Collectors.toList());
    }

    public List<UserActivityReportDTO> getUserActivityReport() {
        // Lấy hoạt động từ Account
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .map(account -> new UserActivityReportDTO(account.getFullname(), account.getEmail(), account.getStatus()))
                .collect(Collectors.toList());
    }
}
