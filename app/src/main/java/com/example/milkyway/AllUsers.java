package com.example.milkyway;

public class AllUsers {
    private String name;
    private String email;
    private String pass;
    private boolean isCustomer;

    public AllUsers(){

    }

    public AllUsers(String name, String email, String pass, boolean isCustomer) {
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.isCustomer = isCustomer;
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

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public boolean isCustomer() {
        return isCustomer;
    }

    public void setCustomer(boolean customer) {
        isCustomer = customer;
    }
}
