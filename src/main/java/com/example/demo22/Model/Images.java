package com.example.demo22.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Images {
    @Id
    private int id;
    private String idProduct;
    private String source;
    //private int is_thumbnail_image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
