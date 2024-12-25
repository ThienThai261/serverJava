package com.example.demo22.Controller;

import com.example.demo22.Model.Order;
import com.example.demo22.Model.OrderDetail;
import com.example.demo22.Rep.OrderRepository;
import com.example.demo22.Rep.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/order")
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    // Get all orders
    @GetMapping("/all")
    public ResponseEntity<?> getAllOrders() {
        try {
            List<Order> orders = orderRepository.findAll();
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching orders: " + e.getMessage());
        }
    }

    // Get order by ID with details
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable String id) {
        try {
            Optional<Order> order = orderRepository.findById(id);
            if (order.isPresent()) {
                return ResponseEntity.ok(order.get());
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching order: " + e.getMessage());
        }
    }

    // Create new order
    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody Map<String, Object> orderData) {
        try {
            Order order = new Order();
            order.setId(UUID.randomUUID().toString()); // Generate unique ID
            order.setAddress((String) orderData.get("address"));
            order.setNumberPhone((String) orderData.get("numberPhone"));
            order.setStatus(0); // Initial status
            order.setDateBuy(new Date());
            order.setIdAccount((Integer) orderData.get("idAccount"));

            // Save the order first
            Order savedOrder = orderRepository.save(order);

            // Handle order details
            List<Map<String, Object>> detailsList = (List<Map<String, Object>>) orderData.get("orderDetails");
            List<OrderDetail> orderDetails = new ArrayList<>();

            for (Map<String, Object> detail : detailsList) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setIdOrder(savedOrder.getId());
                orderDetail.setIdProduct((String) detail.get("idProduct"));
                orderDetail.setQuantity((Integer) detail.get("quantity"));
                orderDetail.setPrice((Integer) detail.get("price"));
                orderDetail.setOrder(savedOrder);
                orderDetails.add(orderDetail);
            }

            // Save order details
            orderDetailRepository.saveAll(orderDetails);

            // Fetch the complete order with details
            Optional<Order> completeOrder = orderRepository.findById(savedOrder.getId());
            return ResponseEntity.ok(completeOrder.get());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating order: " + e.getMessage());
        }
    }
    // Update order status
    @PutMapping("/status/{id}")
    public ResponseEntity<?> updateOrderStatus(
            @PathVariable String id,
            @RequestParam int newStatus) {
        try {
            Optional<Order> orderOpt = orderRepository.findById(id);
            if (orderOpt.isPresent()) {
                Order order = orderOpt.get();
                order.setStatus(newStatus);

                // If status is "Delivered" (you can define the status codes)
                if (newStatus == 4) { // Assuming 4 is "Delivered" status
                    order.setDateArrival(new Date());
                }

                Order updatedOrder = orderRepository.save(order);
                return ResponseEntity.ok(updatedOrder);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating order status: " + e.getMessage());
        }
    }

    // Get orders by account ID
    @GetMapping("/account/{accountId}")
    public ResponseEntity<?> getOrdersByAccount(@PathVariable int accountId) {
        try {
            List<Order> orders = orderRepository.findByIdAccount(accountId);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching orders: " + e.getMessage());
        }
    }

    // Get orders by status
    @GetMapping("/status/{status}")
    public ResponseEntity<?> getOrdersByStatus(@PathVariable int status) {
        try {
            List<Order> orders = orderRepository.findByStatus(status);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching orders: " + e.getMessage());
        }
    }

    // Update order
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateOrder(
            @PathVariable String id,
            @RequestBody Map<String, Object> orderData) {
        try {
            Optional<Order> orderOpt = orderRepository.findById(id);
            if (orderOpt.isPresent()) {
                Order order = orderOpt.get();

                if (orderData.containsKey("address")) {
                    order.setAddress((String) orderData.get("address"));
                }
                if (orderData.containsKey("numberPhone")) {
                    order.setNumberPhone((String) orderData.get("numberPhone"));
                }
                if (orderData.containsKey("status")) {
                    order.setStatus((Integer) orderData.get("status"));
                }
                if (orderData.containsKey("dateArrival")) {
                    order.setDateArrival((Date) orderData.get("dateArrival"));
                }

                Order updatedOrder = orderRepository.save(order);
                return ResponseEntity.ok(updatedOrder);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating order: " + e.getMessage());
        }
    }

    // Delete order
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable String id) {
        try {
            if (orderRepository.existsById(id)) {
                orderRepository.deleteById(id);
                return ResponseEntity.ok("Order deleted successfully");
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deleting order: " + e.getMessage());
        }
    }
}
