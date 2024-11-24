package org.example.pl;

public class Product {
    private String name;
    private String category;
    private double price;
    private String description;
    private boolean availability;
    private int stock;

    public Product(String name, String category, double price, String description, boolean availability, int stock) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.description = description;
        this.availability = availability;
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isAvailable() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Category: " + category + ", Price: $" + price;
    }
}