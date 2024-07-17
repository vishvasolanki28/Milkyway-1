package com.example.milkyway.model;

import java.util.ArrayList;

public class Orderr {
    private String sname, date, rmono, caddress, pmethod, cmono, totalprice,pstatus,cuid,ruid,customername;
    private ArrayList<CartModel> items;

    public Orderr(){}

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRmono() {
        return rmono;
    }

    public void setRmono(String rmono) {
        this.rmono = rmono;
    }

    public String getCaddress() {
        return caddress;
    }

    public void setCaddress(String caddress) {
        this.caddress = caddress;
    }

    public String getPmethod() {
        return pmethod;
    }

    public void setPmethod(String pmethod) {
        this.pmethod = pmethod;
    }

    public String getCmono() {
        return cmono;
    }

    public void setCmono(String cmono) {
        this.cmono = cmono;
    }

    public String getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(String totalprice) {
        this.totalprice = totalprice;
    }

    public String getPstatus() {
        return pstatus;
    }

    public void setPstatus(String pstatus) {
        this.pstatus = pstatus;
    }

    public String getCuid() {
        return cuid;
    }

    public void setCuid(String cuid) {
        this.cuid = cuid;
    }

    public String getRuid() {
        return ruid;
    }

    public void setRuid(String ruid) {
        this.ruid = ruid;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }



    public ArrayList<CartModel> getItems() {
        return items;
    }

    public void setItems(ArrayList<CartModel> items) {
        this.items = items;
    }

    public Orderr(String sname, String date, String rmono, String caddress, String pmethod, String cmono, String totalprice, String pstatus, String cuid, String ruid, String customername, ArrayList<CartModel> items) {
        this.sname = sname;
        this.date = date;
        this.rmono = rmono;
        this.caddress = caddress;
        this.pmethod = pmethod;
        this.cmono = cmono;
        this.totalprice = totalprice;
        this.pstatus = pstatus;
        this.cuid = cuid;
        this.ruid = ruid;
        this.customername = customername;

        this.items = items;
    }
}
