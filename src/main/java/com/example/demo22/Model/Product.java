package com.example.demo22.Model;

import jakarta.persistence.*;

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

    @Column(name = "color", length = 50)
    private String color;

    @Column(name = "gender", length = 50)
    private String gender;

    @Column(name = "status")
    private Integer status;

    @Column(name = "idCategory", nullable = false)
    private int idCategory;

    // Constructors
    public Product() {
    }

    public Product(String id, String name, int price, int quantity, String material, String size, String color, String gender, Integer status, int idCategory) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.material = material;
        this.size = size;
        this.color = color;
        this.gender = gender;
        this.status = status;
        this.idCategory = idCategory;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
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
                ", color='" + color + '\'' +
                ", gender='" + gender + '\'' +
                ", status=" + status +
                ", idCategory=" + idCategory +
                '}';
    }
}
