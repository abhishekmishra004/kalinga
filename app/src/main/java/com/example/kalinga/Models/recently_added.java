package com.example.kalinga.Models;

public class recently_added {
    String product_id,product_link;


    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_link(String product_link) {
        this.product_link = product_link;
    }

    public String getProduct_link() {
        return product_link;
    }

    public recently_added(String product_id, String product_link) {
        this.product_id = product_id;
        this.product_link=product_link;
    }
}
