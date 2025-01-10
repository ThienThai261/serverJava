package com.example.demo22.Controller;

import com.example.demo22.Model.Order;
import com.example.demo22.Model.OrderDetail;
import com.example.demo22.Model.Product;
import com.example.demo22.Model.Images;
import com.example.demo22.Rep.OrderRepository;
import com.example.demo22.Rep.ProductRepository;
import com.example.demo22.Rep.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/purchases")
@CrossOrigin(origins = "*")
public class PurchaseHistoryController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ImageRepository imageRepository;

    // DTO for purchased product details
    private static class PurchasedProductDTO {
        private final String orderId;
        private final String productId;
        private final String productName;
        private final int quantity;
        private final int price;
        private final String material;
        private final String size;
        private final String gender;
        private final int categoryId;
        private final String thumbnailUrl;
        private final int orderStatus;
        private final Date orderDate;

        public PurchasedProductDTO(String orderId, String productId, String productName,
                                   int quantity, int price, String material, String size,
                                   String gender, int categoryId, String thumbnailUrl,
                                   int orderStatus, Date orderDate) {
            this.orderId = orderId;
            this.productId = productId;
            this.productName = productName;
            this.quantity = quantity;
            this.price = price;
            this.material = material;
            this.size = size;
            this.gender = gender;
            this.categoryId = categoryId;
            this.thumbnailUrl = thumbnailUrl;
            this.orderStatus = orderStatus;
            this.orderDate = orderDate;
        }

        // Getters
        public String getOrderId() { return orderId; }
        public String getProductId() { return productId; }
        public String getProductName() { return productName; }
        public int getQuantity() { return quantity; }
        public int getPrice() { return price; }
        public String getMaterial() { return material; }
        public String getSize() { return size; }
        public String getGender() { return gender; }
        public int getCategoryId() { return categoryId; }
        public String getThumbnailUrl() { return thumbnailUrl; }
        public int getOrderStatus() { return orderStatus; }
        public Date getOrderDate() { return orderDate; }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserPurchaseHistory(@PathVariable int userId) {
        try {
            // Get all orders for the user
            List<Order> userOrders = orderRepository.findByIdAccount(userId);

            List<PurchasedProductDTO> purchaseHistory = new ArrayList<>();

            for (Order order : userOrders) {
                for (OrderDetail detail : order.getOrderDetails()) {
                    // Get product information
                    Product product = productRepository.findById(detail.getIdProduct())
                            .orElse(null);

                    if (product != null) {
                        // Get thumbnail image
                        String thumbnailUrl = imageRepository.findByIdProduct(product.getId())
                                .stream()
                                .filter(img -> Boolean.TRUE.equals(img.getIsThumbnailImage()))
                                .findFirst()
                                .map(Images::getImageUrl)
                                .orElse(null);

                        // Create DTO with combined information
                        PurchasedProductDTO purchaseDTO = new PurchasedProductDTO(
                                order.getId(),
                                product.getId(),
                                product.getName(),
                                detail.getQuantity(),
                                detail.getPrice(),
                                product.getMaterial(),
                                product.getSize(),
                                product.getGender(),
                                product.getIdCategory(),
                                thumbnailUrl,
                                order.getStatus(),
                                order.getDateBuy()
                        );

                        purchaseHistory.add(purchaseDTO);
                    }
                }
            }

            return ResponseEntity.ok(purchaseHistory);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error fetching purchase history: " + e.getMessage());
        }
    }
}