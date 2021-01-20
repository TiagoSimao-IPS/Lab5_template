package com.pa.refactoring.model;

import java.util.ArrayList;

/**
 * @author patricia.macedo
 */
public class ShoppingCart {

    private double total;
    private boolean terminated;
    private Time time;
    private ArrayList<Product> products;

    public ShoppingCart() {
        terminated = false;
        time = new Time();
        this.products = new ArrayList<>();
    }

    public double getTotal() {
        total = 0;
        for (Product p : products) {
            total += p.getCost();
        }
        return total;
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public ArrayList<Product> getProducts() {
        return this.products;
    }

    public boolean isTerminated() {
        return terminated;
    }

    public void terminate() {
        terminated = true;
        time = new Time();
    }

    public String getDateStr() {
        String dateStr = String.format("%02d/%02d/%4d %02d:%02d", time.getDay(), time.getMonth(), time.getYear(), time.getHours(), time.getMinutes());
        return dateStr;
    }
}
