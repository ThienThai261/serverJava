package com.example.demo22.Model;

public class UserActivityReportDTO {
    private String fullName;
    private String email;
    private int status;

    public UserActivityReportDTO(String fullName, String email, int status) {
        this.fullName = fullName;
        this.email = email;
        this.status = status;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
// Getters and Setters
}
