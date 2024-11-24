package org.example.pl;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.*;

public class ProductDetailsFrame extends JFrame {
    public ProductDetailsFrame(Product product) {
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

        add(detailsPanel);
        setVisible(true);
    }
}