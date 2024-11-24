package org.example.pl;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;

public class ProductDetailsFrame extends JFrame {
    public ProductDetailsFrame(Product product, ShoppingCart shoppingCart) {
        setTitle("Product Details");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel detailsPanel = new JPanel();
        detailsPanel.setBackground(new Color(173, 216, 230)); // Light blue background color
        detailsPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Product details
        gbc.gridx = 0;
        gbc.gridy = 0;
        detailsPanel.add(new JLabel("Name:"), gbc);

        gbc.gridx = 1;
        detailsPanel.add(new JLabel(product.getName()), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        detailsPanel.add(new JLabel("Category:"), gbc);

        gbc.gridx = 1;
        detailsPanel.add(new JLabel(product.getCategory()), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        detailsPanel.add(new JLabel("Price:"), gbc);

        gbc.gridx = 1;
        detailsPanel.add(new JLabel("$" + product.getPrice()), gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        detailsPanel.add(new JLabel("Description:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        JTextArea descriptionArea = new JTextArea(product.getDescription(), 3, 20);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setLineWrap(true);
        descriptionArea.setEditable(false);
        detailsPanel.add(new JScrollPane(descriptionArea), gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        detailsPanel.add(new JLabel("Availability:"), gbc);

        gbc.gridx = 1;
        detailsPanel.add(new JLabel(product.isAvailable() ? "In Stock" : "Out of Stock"), gbc);

        // Quantity field
        gbc.gridx = 0;
        gbc.gridy = 5;
        detailsPanel.add(new JLabel("Quantity:"), gbc);

        gbc.gridx = 1;
        JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        detailsPanel.add(quantitySpinner, gbc);

        // Add to cart button
        gbc.gridx = 1;
        gbc.gridy = 6;
        JButton addToCartButton = new JButton("Add to Cart");
        addToCartButton.addActionListener(e -> {
            int quantity = (int) quantitySpinner.getValue();
            shoppingCart.addProduct(product, quantity);
            JOptionPane.showMessageDialog(this, "Product added to cart!");
        });
        detailsPanel.add(addToCartButton, gbc);

        add(detailsPanel);
        setVisible(true);
    }
}