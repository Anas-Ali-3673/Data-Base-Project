package org.example.pl;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.*;

public class DashboardFrame extends JFrame {
    private List<Product> products;
    private ShoppingCart shoppingCart;

    public DashboardFrame(List<Product> products, ShoppingCart shoppingCart) {
        this.products = products;
        this.shoppingCart = shoppingCart;

        setTitle("Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel dashboardPanel = new JPanel();
        dashboardPanel.setBackground(new Color(173, 216, 230)); // Light blue background color
        dashboardPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Product Management button
        gbc.gridx = 0;
        gbc.gridy = 0;
        JButton productManagementButton = new JButton("Product Management");
        productManagementButton.addActionListener(e -> new InventoryManagementFrame(products));
        dashboardPanel.add(productManagementButton, gbc);

        // User Management button
        gbc.gridx = 0;
        gbc.gridy = 1;
        JButton userManagementButton = new JButton("User Management");
        userManagementButton.addActionListener(e -> new UserManagementFrame());
        dashboardPanel.add(userManagementButton, gbc);

        // Order Management button
        gbc.gridx = 0;
        gbc.gridy = 2;
        JButton orderManagementButton = new JButton("Order Management");
        orderManagementButton.addActionListener(e -> new OrderManagementFrame());
        dashboardPanel.add(orderManagementButton, gbc);

        // Shopping Cart button
        gbc.gridx = 0;
        gbc.gridy = 3;
        JButton cartButton = new JButton("View Cart");
        cartButton.addActionListener(e -> new ShoppingCartFrame(shoppingCart));
        dashboardPanel.add(cartButton, gbc);

        add(dashboardPanel);
        setVisible(true);
    }
}