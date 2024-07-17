package com.example.milkyway;

public class Customer {
    private String name;
    private String email;
    private String mono;
    private String address;
    private String city;
    private String pass;

    public Customer(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMono() {
        return mono;
    }

    public void setMono(String mono) {
        this.mono = mono;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Customer(String name, String email, String mono, String address, String city, String pass) {
        this.name = name;
        this.email = email;
        this.mono = mono;
        this.address = address;
        this.city = city;
        this.pass = pass;
    }


}
