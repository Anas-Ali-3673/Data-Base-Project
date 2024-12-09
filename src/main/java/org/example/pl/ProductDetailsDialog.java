package org.example.pl;

import org.example.dto.ProductDto;
import org.example.dal.ShoppingCart;
import org.example.bll.PromotionManager;
import org.example.dto.PromotionDto;

import javax.swing.*;
import java.awt.*;

public class ProductDetailsDialog extends JDialog {
    private ShoppingCart shoppingCart;
    private ProductDto product;
    private PromotionManager promotionManager;

    public ProductDetailsDialog(JFrame parent, ProductDto product, ShoppingCart shoppingCart) {
        super(parent, "Product Details", true);
        this.product = product;
        this.shoppingCart = shoppingCart;
        this.promotionManager = new PromotionManager();

        setSize(400, 300);
        setLocationRelativeTo(parent);

        // Main Panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        add(mainPanel);

        // Product Info Section
        JPanel productInfoPanel = new JPanel(new GridLayout(6, 1, 5, 5));
        productInfoPanel.setBorder(BorderFactory.createTitledBorder("Product Information"));
        productInfoPanel.add(createStyledLabel("Name: " + product.getName()));
        productInfoPanel.add(createStyledLabel("Description: " + product.getDescription()));
        productInfoPanel.add(createStyledLabel("Price: $" + product.getPrice()));
        productInfoPanel.add(createStyledLabel("Stock: " + product.getStock()));
        productInfoPanel.add(createStyledLabel("Category: " + product.getCategory()));
        productInfoPanel.add(createStyledLabel("Available: " + (product.isAvailable() ? "Yes" : "No")));
        mainPanel.add(productInfoPanel, BorderLayout.CENTER);

        // Promo Code Section
        JPanel promoPanel = new JPanel(new BorderLayout(10, 10));
        promoPanel.setBorder(BorderFactory.createTitledBorder("Apply Promo Code"));

        JTextField promoCodeField = new JTextField();
        promoCodeField.setFont(new Font("Arial", Font.PLAIN, 14));
        promoPanel.add(new JLabel("Promo Code:"), BorderLayout.WEST);
        promoPanel.add(promoCodeField, BorderLayout.CENTER);

        JButton applyPromoButton = new JButton("Apply Promo");
        styleButton(applyPromoButton, new Color(46, 204, 113), Color.WHITE);
        promoPanel.add(applyPromoButton, BorderLayout.EAST);

        applyPromoButton.addActionListener(e -> {
            String promoCode = promoCodeField.getText().trim();
            PromotionDto promotion = promotionManager.getPromotionByCode(promoCode);
            if (promotion != null && promotion.isActive()) {
                double discountedPrice = product.getPrice() * (1 - promotion.getDiscountPercentage() / 100);
                JOptionPane.showMessageDialog(this, "Discount applied! New Price: $" + discountedPrice,
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid or inactive promo code.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        mainPanel.add(promoPanel, BorderLayout.SOUTH);

        // Footer Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton closeButton = new JButton("Close");
        styleButton(closeButton, new Color(231, 76, 60), Color.RED);

        closeButton.addActionListener(e -> dispose());
        buttonPanel.add(closeButton);

        mainPanel.add(buttonPanel, BorderLayout.PAGE_END);
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(new Color(44, 62, 80));
        return label;
    }

    private void styleButton(JButton button, Color bgColor, Color fgColor) {
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
    }
}
