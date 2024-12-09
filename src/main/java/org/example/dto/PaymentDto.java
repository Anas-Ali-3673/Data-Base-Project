package org.example.dto;

public class PaymentDto {
    private int userId;
    private String paymentMethod;
    private String accountNumber;
    private double totalAmount;

    public PaymentDto(int userId, String paymentMethod, String accountNumber, double totalAmount) {
        this.userId = userId;
        this.paymentMethod = paymentMethod;
        this.accountNumber = accountNumber;
        this.totalAmount = totalAmount;
    }

    // Getters and Setters
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
}