package com.example.cropfit;

import java.io.Serializable;

public class Product implements Serializable {
    //  String title;
    String name;
    String price;
    String icon;
    String desc;
    int quantity=1;

    public Product() {
    }

    public Product(String name, String price, String icon, String desc) {
        this.name = name;
        this.price = price;
        this.icon = icon;
        this.desc = desc;
//        this.quantity = quantity;
    }

    public String getName()
    {
        return name;

    }

    public void setName(String name) {

        this.name = name;
    }

    /* public String getImageUrl()
     {
         return imageUrl;
     }

     public void setImageUrl(String imageUrl) {

         this.imageUrl = imageUrl;
     }*/

    public String getPrice() {

        return price;
    }

    public void setPrice(String price) {

        this.price = price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", icon='" + icon + '\'' +
                ", desc='" + desc + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
