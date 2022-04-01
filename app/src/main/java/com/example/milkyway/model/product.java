package com.example.milkyway.model;

public class product {
   String name,price,quantity,image,key;

    public product(String name, String price, String quantity, String image, String key) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
        this.key = key;
    }

    public product(){}
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
