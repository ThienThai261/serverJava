package com.example.demo22.Model;


import jakarta.persistence.*;

@Entity
@Table(name = "images")
public class Images {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "idProduct", length = 10, nullable = false)
    private String idProduct;

    @Column(name = "source", length = 255, nullable = false)
    private String source;

    @Column(name = "is_thumbnail_image")
    private Boolean isThumbnailImage;

    // Constructors
    public Images() {
    }

    public Images(String idProduct, String source, Boolean isThumbnailImage) {
        this.idProduct = idProduct;
        this.source = source;
        this.isThumbnailImage = isThumbnailImage;
    }

    // Getters and Setters

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


    public Boolean getIsThumbnailImage() {
        return isThumbnailImage;
    }

    public void setIsThumbnailImage(Boolean isThumbnailImage) {
        this.isThumbnailImage = isThumbnailImage;
    }

    @Override
    public String toString() {
        return "Images{" +
                "id=" + id +
                ", idProduct='" + idProduct + '\'' +
                ", source='" + source + '\'' +
                ", isThumbnailImage=" + isThumbnailImage +
                '}';
    }
    public String getImageUrl() {
        if (source != null) {
            System.out.println("Original source: " + source);

            // Xử lý đường dẫn ./assets thành /assets
            if (source.startsWith("./")) {
                return source.substring(1); // Bỏ dấu chấm ở đầu
            }
            // Nếu không có dấu / ở đầu thì thêm vào
            if (!source.startsWith("/")) {
                return "/" + source;
            }
            return source;
        }
        return null;
    }

}
