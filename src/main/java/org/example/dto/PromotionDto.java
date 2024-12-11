package org.example.dto;

public class PromotionDto {
    private String code;
    private double discountPercentage;
    private boolean isActive;
    private Integer productId; // Nullable, specific product ID
    private Integer userId; // Nullable, specific user ID

    public PromotionDto(String code, double discountPercentage, boolean isActive, Integer productId, Integer userId) {
        this.code = code;
        this.discountPercentage = discountPercentage;
        this.isActive = isActive;
        this.productId = productId;
        this.userId = userId;
    }

    // Getters and Setters
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public double getDiscountPercentage() { return discountPercentage; }
    public void setDiscountPercentage(double discountPercentage) { this.discountPercentage = discountPercentage; }
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
    public Integer getProductId() { return productId; }
    public void setProductId(Integer productId) { this.productId = productId; }
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
}