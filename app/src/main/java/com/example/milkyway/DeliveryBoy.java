package com.example.milkyway;

public class DeliveryBoy {
    private String name;
    private String email;
    private String mono;
    private String sname;
    private String address;
    private String city;
    private String acc;
    private String ifsc;
    private String pass;

    public DeliveryBoy(){

    }

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

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
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

    public String getAcc() {
        return acc;
    }

    public void setAcc(String acc) {
        this.acc = acc;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public DeliveryBoy(String name, String email, String mono, String sname, String address, String city, String acc, String ifsc, String pass) {
        this.name = name;
        this.email = email;
        this.mono = mono;
        this.sname = sname;
        this.address = address;
        this.city = city;
        this.acc = acc;
        this.ifsc = ifsc;
        this.pass = pass;
    }
}