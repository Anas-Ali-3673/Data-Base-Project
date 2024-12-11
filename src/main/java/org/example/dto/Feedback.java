package org.example.dto;

public class Feedback {
    private int productId;
    private String customerName;
    private String comments;
    private int rating;

    public Feedback(int productId, String customerName, String comments, int rating) {
        this.productId = productId;
        this.customerName = customerName;
        this.comments = comments;
        this.rating = rating;
    }

    // Getters and setters
    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }
    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }
}