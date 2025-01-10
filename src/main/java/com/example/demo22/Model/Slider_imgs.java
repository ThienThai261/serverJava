package com.example.demo22.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "slider_imgs")
public class Slider_imgs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "source", length = 255, nullable = false)
    private String source;

    public Slider_imgs(int id, String source) {
        this.id = id;
        this.source = source;
    }

    public Slider_imgs() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "Slider_imgs{" +
                "id=" + id +
                ", source='" + source + '\'' +
                '}';
    }
}
