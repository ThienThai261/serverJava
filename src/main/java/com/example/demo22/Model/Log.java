package com.example.demo22.Model;

import jakarta.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "log")
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "ip", length = 50)
    private String ip;

    @Column(name = "level", length = 50)
    private String level;

    @Column(name = "address", length = 50)
    private String address;

    @Column(name = "preValue", length = 50, columnDefinition = "varchar(50) COLLATE utf8mb4_bin")
    private String preValue;

    @Column(name = "value", length = 50, columnDefinition = "varchar(50) COLLATE utf8mb4_bin")
    private String value;

    @Column(name = "date")
    private Date date;

    @Column(name = "country", length = 50)
    private String country;

    @Column(name = "status", length = 50)
    private String status;

    // Getters and Setters

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPreValue() {
        return preValue;
    }

    public void setPreValue(String preValue) {
        this.preValue = preValue;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}


