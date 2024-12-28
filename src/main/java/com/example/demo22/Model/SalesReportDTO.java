package com.example.demo22.Model;

public class SalesReportDTO {
    private double totalRevenue;
    private String startDate;
    private String endDate;

    public SalesReportDTO(double totalRevenue, String startDate, String endDate) {
        this.totalRevenue = totalRevenue;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    // Getters and Setters
}
