package com.example.kalinga.Models;

public class home_singleproduct {
    private String image;
    private String product_name,seller_name,product_id;
    private int price;

    public home_singleproduct(String image, String product_name, String seller_name, int price, String product_id) {
        this.image = image;
        this.product_name = product_name;
        this.seller_name = seller_name;
        this.price = price;
        this.product_id = product_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getSeller_name() {
        return seller_name;
    }

    public void setSeller_name(String seller_name) {
        this.seller_name = seller_name;
    }

    public int getPrice() {
        return price;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getProduct_id() {
        return product_id;
    }
}
