package com.example.demo22.Controller;

import com.example.demo22.Model.Images;
import com.example.demo22.Rep.ImageRepository;
import com.example.demo22.Rep.ProductRepository;
import com.example.demo22.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/imgs")
@CrossOrigin(origins = "*")
public class ImageController {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ImageService imageService;


    private final String UPLOAD_DIR = "src/main/resources/assets/images/product_img/";

    @PostMapping("/upload/{productId}")
    public ResponseEntity<?> uploadImages(
            @PathVariable String productId,
            @RequestParam("files") MultipartFile[] files,
            @RequestParam(value = "thumbnailIndex", defaultValue = "0") int thumbnailIndex) {

        try {
            // Check if product exists
            if (!productRepository.existsById(productId)) {
                return ResponseEntity.badRequest().body("Product not found with ID: " + productId);
            }

            // Create upload directory if it doesn't exist
            File directory = new File(UPLOAD_DIR);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            List<Images> savedImages = new ArrayList<>();

            // Process each uploaded file
            for (int i = 0; i < files.length; i++) {
                MultipartFile file = files[i];

                // Generate unique filename
                String fileName = productId + "(" + (i + 1) + ")" + getFileExtension(file.getOriginalFilename());
                Path filePath = Paths.get(UPLOAD_DIR + fileName);

                // Save file to directory
                try {
                    Files.write(filePath, file.getBytes());
                } catch (IOException e) {
                    return ResponseEntity.internalServerError()
                            .body("Failed to save file: " + fileName + ". Error: " + e.getMessage());
                }

                // Create and save image record in database
                Images image = new Images();
                image.setIdProduct(productId);
                image.setSource("./assets/images/product_img/" + fileName);
                image.setIsThumbnailImage(i == thumbnailIndex);

                savedImages.add(imageRepository.save(image));
            }

            return ResponseEntity.ok().body(savedImages);

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Failed to upload images. Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{imageId}")
    public ResponseEntity<?> deleteImage(@PathVariable int imageId) {
        try {
            Images image = imageRepository.findById(imageId).orElse(null);
            if (image == null) {
                return ResponseEntity.badRequest().body("Image not found with ID: " + imageId);
            }

            // Get filename from source path
            String fileName = image.getSource().substring(image.getSource().lastIndexOf("/") + 1);
            Path filePath = Paths.get(UPLOAD_DIR + fileName);

            // Delete file from filesystem
            try {
                Files.deleteIfExists(filePath);
            } catch (IOException e) {
                return ResponseEntity.internalServerError()
                        .body("Failed to delete file: " + fileName + ". Error: " + e.getMessage());
            }

            // Delete record from database
            imageRepository.deleteById(imageId);

            return ResponseEntity.ok().body("Image deleted successfully");

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Failed to delete image. Error: " + e.getMessage());
        }
    }
    @GetMapping
    List<Images> getListImg() { return imageService.getListImg(); }

    @GetMapping("/{id}")
    Images getImg(@PathVariable("id") int id) {
        return imageService.getImg(id);
    }

    private String getFileExtension(String fileName) {
        if (fileName == null) return "";
        int lastDotIndex = fileName.lastIndexOf(".");
        return lastDotIndex == -1 ? "" : fileName.substring(lastDotIndex);
    }
}