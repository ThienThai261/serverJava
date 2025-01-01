package com.example.demo22.Controller;

import com.example.demo22.Model.Product;
import com.example.demo22.Model.Images;
import com.example.demo22.Rep.ProductRepository;
import com.example.demo22.Rep.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/product")
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ImageRepository imageRepository;

    // Create or Add New Product
    @PostMapping(path = "/add")
    public @ResponseBody String addNewProduct(@RequestParam String id,
                                              @RequestParam String name,
                                              @RequestParam int price,
                                              @RequestParam int quantity,
                                              @RequestParam(required = false) String material,
                                              @RequestParam(required = false) String size,
                                              @RequestParam(required = false) String gender,
                                              @RequestParam(required = false) Integer status,
                                              @RequestParam int id_category) {
        try {
            Product product = new Product();
            product.setId(id);
            product.setName(name);
            product.setPrice(price);
            product.setQuantity(quantity);
            product.setMaterial(material);
            product.setSize(size);
            product.setGender(gender);
            product.setStatus(status);
            product.setIdCategory(id_category);

            productRepository.save(product);
            return "Product saved successfully.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error saving product: " + e.getMessage();
        }
    }

    // Retrieve All Products with Images
    @GetMapping(path = "/all")
    public @ResponseBody List<ProductWithImages> getAllProducts() {
        List<Product> products = (List<Product>) productRepository.findAll();
        return products.stream()
                .map(this::convertToProductWithImages)
                .collect(Collectors.toList());
    }

    // Retrieve a Product by ID with Images
    @GetMapping(path = "/{id}")
    public @ResponseBody ProductWithImages getProductById(@PathVariable("id") String id) {
        Optional<Product> productOpt = productRepository.findById(id);
        return productOpt.map(this::convertToProductWithImages)
                .orElse(null);
    }

    // Helper class to include images with product
    private static class ProductWithImages {
        private Product product;
        private List<String> imageUrls;
        private String thumbnailUrl;

        public ProductWithImages(Product product, List<String> imageUrls, String thumbnailUrl) {
            this.product = product;
            this.imageUrls = imageUrls;
            this.thumbnailUrl = thumbnailUrl;
        }

        public Product getProduct() {
            return product;
        }

        public List<String> getImageUrls() {
            return imageUrls;
        }

        public String getThumbnailUrl() {
            return thumbnailUrl;
        }
    }

    // Helper method to convert Product to ProductWithImages
    private ProductWithImages convertToProductWithImages(Product product) {
        // Log cho debugging
        System.out.println("Converting product: " + product.getId());

        List<Images> images = imageRepository.findByIdProduct(product.getId());
        System.out.println("Found " + images.size() + " images for product " + product.getId());

        // Get all image URLs
        List<String> imageUrls = images.stream()
                .map(img -> {
                    String url = img.getImageUrl();
                    System.out.println("Processing image: " + img.getId() + ", URL: " + url);
                    return url;
                })
                .collect(Collectors.toList());

        // Get thumbnail URL
        String thumbnailUrl = images.stream()
                .filter(img -> Boolean.TRUE.equals(img.getIsThumbnailImage()))
                .findFirst()
                .map(Images::getImageUrl)
                .orElse(imageUrls.isEmpty() ? null : imageUrls.get(0));

        return new ProductWithImages(product, imageUrls, thumbnailUrl);
    }

    // Update methods remain the same
    @PutMapping(path = "/update/{id}")
    public @ResponseBody String updateProduct(@PathVariable("id") String id,
                                              @RequestParam String name,
                                              @RequestParam int price,
                                              @RequestParam int quantity,
                                              @RequestParam(required = false) String material,
                                              @RequestParam(required = false) String size,
                                              @RequestParam(required = false) String gender,
                                              @RequestParam(required = false) Integer status,
                                              @RequestParam int id_category) {
        if (!productRepository.existsById(id)) {
            return "Product with ID " + id + " does not exist.";
        }
        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            product.setName(name);
            product.setPrice(price);
            product.setQuantity(quantity);
            product.setMaterial(material);
            product.setSize(size);
            product.setGender(gender);
            product.setStatus(status);
            product.setIdCategory(id_category);
            productRepository.save(product);
            return "Product updated successfully.";
        }
        return "Failed to update product.";
    }

    @DeleteMapping("/{id}")
    public @ResponseBody String deleteProduct(@PathVariable("id") String id) {
        try {
            if (!productRepository.existsById(id)) {
                throw new IllegalArgumentException("Product with ID " + id + " does not exist.");
            }
            productRepository.deleteById(id);
            return "Product deleted successfully.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to delete product: " + e.getMessage();
        }
    }
}