package org.example.dto;

public class ProductDto {
    private int id;
    private String name;
    private String description;
    private double price;
    private int stock;
//    private  String category;
//    private boolean isAvailable;

    public ProductDto(int id, String name, String description, double price, int stock) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
//        this.category = category;
//        this.isAvailable = isAvailable;
    }
    public ProductDto() {
        // No-argument constructor
    }



    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

//    public String getCategory() { return category; }
//    public void setCategory(String category) { this.category = category; }
//
//    public boolean isAvailable() { return isAvailable; }
//    public void setAvailable(boolean available) { isAvailable = available; }
}