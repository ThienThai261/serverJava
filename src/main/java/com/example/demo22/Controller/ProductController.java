package com.example.demo22.Controller;

import com.example.demo22.Model.Product;
import com.example.demo22.Rep.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/product")
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    // Create or Add New Product
    @PostMapping(path = "/add")
    public @ResponseBody String addNewProduct(@RequestParam String id,
                                              @RequestParam String name,
                                              @RequestParam int price,
                                              @RequestParam int quantity,
                                              @RequestParam(required = false) String material,
                                              @RequestParam(required = false) String size,
                                              @RequestParam(required = false) String color,
                                              @RequestParam(required = false) String gender,
                                              @RequestParam(required = false) Integer status,
                                              @RequestParam int idCategory) {
        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setMaterial(material);
        product.setSize(size);
        product.setColor(color);
        product.setGender(gender);
        product.setStatus(status);
        product.setIdCategory(idCategory);

        productRepository.save(product);
        return "Product saved successfully.";
    }

    // Retrieve All Products
    @GetMapping(path = "/all")
    public @ResponseBody Iterable<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Retrieve a Product by ID
    @GetMapping(path = "/{id}")
    public @ResponseBody Optional<Product> getProductById(@PathVariable("id") String id) {
        return productRepository.findById(id);
    }

    // Update an Existing Product
    @PutMapping(path = "/update/{id}")
    public @ResponseBody String updateProduct(@PathVariable("id") String id,
                                              @RequestParam String name,
                                              @RequestParam int price,
                                              @RequestParam int quantity,
                                              @RequestParam(required = false) String material,
                                              @RequestParam(required = false) String size,
                                              @RequestParam(required = false) String color,
                                              @RequestParam(required = false) String gender,
                                              @RequestParam(required = false) Integer status,
                                              @RequestParam int idCategory) {
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
            product.setColor(color);
            product.setGender(gender);
            product.setStatus(status);
            product.setIdCategory(idCategory);
            productRepository.save(product);
            return "Product updated successfully.";
        }
        return "Failed to update product.";
    }

    // Delete a Product by ID
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
