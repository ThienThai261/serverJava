package com.example.demo22.Model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @Column(name = "id", length = 10, nullable = false)
    private String id;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "material", length = 50)
    private String material;

    @Column(name = "size", length = 50)
    private String size;


    @Column(name = "gender", length = 50)
    private String gender;

    @Column(name = "status")
    private Integer status;

    @Column(name = "id_category", nullable = false) // Update as needed
    private Integer id_category;


    // Constructors
    public Product() {
    }

    public Product(String id, String name, int price, int quantity, String material, String size, String gender, Integer status, int id_category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.material = material;
        this.size = size;
        this.gender = gender;
        this.status = status;
        this.id_category = id_category;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }



    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public int getIdCategory() {
        return id_category;
    }

    public void setIdCategory(int idCategory) {
        this.id_category = idCategory;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", material='" + material + '\'' +
                ", size='" + size + '\'' +
                ", gender='" + gender + '\'' +
                ", status=" + status +
                ", idCategory=" + id_category +
                '}';
    }
    @OneToMany(fetch = FetchType.EAGER)  // Đổi thành EAGER để load images ngay
    @JoinColumn(name = "idProduct", referencedColumnName = "id")
    private List<Images> images = new ArrayList<>(); // Khởi tạo list trống
    public List<Images> getImages() {
        return images;
    }

    public void setImages(List<Images> images) {
        this.images = images;
    }

    // Thêm phương thức tiện ích để lấy ảnh thumbnail
    public String getThumbnailUrl() {
        if (images != null && !images.isEmpty()) {
            return images.stream()
                    .filter(img -> Boolean.TRUE.equals(img.getIsThumbnailImage()))
                    .findFirst()
                    .map(Images::getImageUrl)
                    .orElse(images.get(0).getImageUrl());
        }
        return null;
    }
}
