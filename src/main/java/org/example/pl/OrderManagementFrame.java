package org.example.pl;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.*;

public class OrderManagementFrame extends JFrame {
    public OrderManagementFrame() {
        setTitle("Order Management");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel orderPanel = new JPanel();
        orderPanel.setBackground(new Color(173, 216, 230)); // Light blue background color
        orderPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Add order management fields and buttons here

        add(orderPanel);
        setVisible(true);
    }
}