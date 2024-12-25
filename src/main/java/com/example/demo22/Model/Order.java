package com.example.demo22.Model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @Column(name = "id", length = 50)
    private String id;

    @Column(name = "address")
    private String address;

    @Column(name = "numberPhone", nullable = false)
    private String numberPhone;

    @Column(name = "status", nullable = false)
    private int status;

    @Column(name = "dateBuy")
    @Temporal(TemporalType.DATE)
    private Date dateBuy;

    @Column(name = "dateArrival")
    @Temporal(TemporalType.DATE)
    private Date dateArrival;

    @Column(name = "idAccount", nullable = false)
    private int idAccount;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OrderDetail> orderDetails;

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getNumberPhone() { return numberPhone; }
    public void setNumberPhone(String numberPhone) { this.numberPhone = numberPhone; }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public Date getDateBuy() { return dateBuy; }
    public void setDateBuy(Date dateBuy) { this.dateBuy = dateBuy; }

    public Date getDateArrival() { return dateArrival; }
    public void setDateArrival(Date dateArrival) { this.dateArrival = dateArrival; }

    public int getIdAccount() { return idAccount; }
    public void setIdAccount(int idAccount) { this.idAccount = idAccount; }

    public List<OrderDetail> getOrderDetails() { return orderDetails; }
    public void setOrderDetails(List<OrderDetail> orderDetails) { this.orderDetails = orderDetails; }
}
