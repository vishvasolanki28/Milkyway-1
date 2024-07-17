package com.example.milkyway.model;

import java.util.ArrayList;

public class Deemo {

    private String shop,ruid,price,date,method,status,rmono,cuid,customername,customeraddress,cmono,deliver,key;
    private ArrayList<CartModel> items;

    public Deemo(){

    }
    public Deemo(String shop, String ruid, String price, String date, String method, String status,
                 String rmono, String cuid, String customername, String customeraddress, String cmono,
                 String deliver, String key, ArrayList<CartModel> items) {
        this.shop = shop;
        this.ruid = ruid;
        this.price = price;
        this.date = date;
        this.method = method;
        this.status = status;
        this.rmono = rmono;
        this.cuid = cuid;
        this.customername = customername;
        this.customeraddress = customeraddress;
        this.cmono = cmono;
        this.deliver = deliver;
        this.key = key;
        this.items = items;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public String getRuid() {
        return ruid;
    }

    public void setRuid(String ruid) {
        this.ruid = ruid;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRmono() {
        return rmono;
    }

    public void setRmono(String rmono) {
        this.rmono = rmono;
    }

    public String getCuid() {
        return cuid;
    }

    public void setCuid(String cuid) {
        this.cuid = cuid;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getCustomeraddress() {
        return customeraddress;
    }

    public void setCustomeraddress(String customeraddress) {
        this.customeraddress = customeraddress;
    }

    public String getCmono() {
        return cmono;
    }

    public void setCmono(String cmono) {
        this.cmono = cmono;
    }

    public String getDeliver() {
        return deliver;
    }

    public void setDeliver(String deliver) {
        this.deliver = deliver;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ArrayList<CartModel> getItems() {
        return items;
    }

    public void setItems(ArrayList<CartModel> items) {
        this.items = items;
    }
}
