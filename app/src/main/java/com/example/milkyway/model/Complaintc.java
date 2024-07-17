package com.example.milkyway.model;

public class Complaintc {
    String order;
    String email;
    String city;
    String add;
    String mobile;
    String complaint;

    public Complaintc(){}

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public Complaintc(String order, String email, String city, String add, String mobile, String complaint) {
        this.order = order;
        this.email = email;
        this.city = city;
        this.add = add;
        this.mobile = mobile;
        this.complaint = complaint;
    }
}
