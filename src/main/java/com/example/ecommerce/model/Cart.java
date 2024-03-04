package com.example.ecommerce.model;

import java.util.ArrayList;
import java.util.UUID;

public class Cart {
    private String id;
    private ArrayList<Product> products;

    private long lastAccessed;

    public Cart() {
        this.id = UUID.randomUUID().toString();
        this.lastAccessed = System.currentTimeMillis();
        this.products = new ArrayList<>();
    }

    public long getLastAccessed() {
        return lastAccessed;
    }

    public void setLastAccessed(long lastAccessed) {
        this.lastAccessed = lastAccessed;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProduct(ArrayList<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", products=" + products +
                '}';
    }
}
