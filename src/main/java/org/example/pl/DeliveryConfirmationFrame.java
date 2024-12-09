package org.example.pl;

import java.awt.*;
import java.util.List;
import org.example.bll.OrderManager;
import org.example.dto.OrderItem;
import org.example.dto.OrderStatus;
import org.example.dto.ProductDto;
import javax.swing.*;

public class DeliveryConfirmationFrame extends JFrame {
    private final OrderManager orderManager;
    private final int orderId;

    public DeliveryConfirmationFrame(OrderManager orderManager, int orderId) {
        this.orderManager = orderManager;
        this.orderId = orderId;
        setTitle("Confirm Delivery");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {
        // Panel for content
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Order confirmation label
        JLabel orderLabel = new JLabel("Confirm delivery for Order ID: " + orderId + "?");
        orderLabel.setFont(new Font("Arial", Font.BOLD, 14));
        orderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(orderLabel);

        // Add space between elements
        panel.add(Box.createVerticalStrut(20));

        // Confirm button with styling
        JButton confirmButton = new JButton("Confirm");
        confirmButton.setBackground(new Color(46, 204, 113));
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setFont(new Font("Arial", Font.BOLD, 14));
        confirmButton.setFocusPainted(false);
        confirmButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        confirmButton.addActionListener(e -> confirmDelivery());
        panel.add(confirmButton);

        // Cancel button
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBackground(new Color(231, 76, 60));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFont(new Font("Arial", Font.BOLD, 14));
        cancelButton.setFocusPainted(false);
        cancelButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        cancelButton.addActionListener(e -> dispose());
        panel.add(Box.createVerticalStrut(10)); // space between buttons
        panel.add(cancelButton);

        // Adding the panel to the frame
        add(panel);

        // Center window on screen
        setLocationRelativeTo(null);
    }

    private void confirmDelivery() {
        if (orderManager.updateOrderStatus(orderId, OrderStatus.DELIVERED)) {
            JOptionPane.showMessageDialog(this, "Order status updated to DELIVERED.", "Success", JOptionPane.INFORMATION_MESSAGE);
            promptForFeedback();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update order status.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        dispose();
    }

    private void promptForFeedback() {
        List<OrderItem> orderItems = orderManager.getOrderItemsByOrderId(orderId);
        for (OrderItem orderItem : orderItems) {
            ProductDto product = new ProductDto();
            product.setId(orderItem.getProductId());
            product.setName("Product Name");  // Assuming placeholder for actual product name
            product.setDescription("Product Description"); // Placeholder for actual product description
            product.setPrice(orderItem.getPrice());
            product.setStock(orderItem.getQuantity());
            
            // Open feedback window for each product
            JFrame feedbackFrame = new FeedbackFrame(product);
            feedbackFrame.setVisible(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String orderIdStr = JOptionPane.showInputDialog(null, "Enter Order ID:", "Order ID", JOptionPane.QUESTION_MESSAGE);
            if (orderIdStr != null && !orderIdStr.trim().isEmpty()) {
                try {
                    int orderId = Integer.parseInt(orderIdStr.trim());
                    OrderManager orderManager = new OrderManager();
                    new DeliveryConfirmationFrame(orderManager, orderId).setVisible(true);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid Order ID. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
