package org.example.pl;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ShoppingCartFrame extends JFrame {
    private ShoppingCart shoppingCart;
    private JTable cartTable;
    private DefaultTableModel tableModel;
    private JLabel totalCostLabel;

    public ShoppingCartFrame(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;

        setTitle("Shopping Cart");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel cartPanel = new JPanel();
        cartPanel.setBackground(new Color(173, 216, 230)); // Light blue background color
        cartPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Cart table
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        tableModel = new DefaultTableModel(new Object[]{"Name", "Category", "Price", "Quantity"}, 0);
        cartTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(cartTable);
        cartPanel.add(scrollPane, gbc);

        // Display cart items
        displayCartItems();

        // Total cost label
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        totalCostLabel = new JLabel("Total Cost: $" + shoppingCart.getTotalCost());
        cartPanel.add(totalCostLabel, gbc);

        // Remove button
        gbc.gridx = 1;
        gbc.gridy = 1;
        JButton removeButton = new JButton("Remove Selected");
        removeButton.addActionListener(e -> {
            int selectedRow = cartTable.getSelectedRow();
            if (selectedRow >= 0) {
                String productName = (String) tableModel.getValueAt(selectedRow, 0);
                Product product = getProductByName(productName);
                if (product != null) {
                    shoppingCart.removeProduct(product);
                    displayCartItems();
                    totalCostLabel.setText("Total Cost: $" + shoppingCart.getTotalCost());
                }
            }
        });
        cartPanel.add(removeButton, gbc);

        add(cartPanel);
        setVisible(true);
    }

    private void displayCartItems() {
        tableModel.setRowCount(0); // Clear existing rows
        List<CartItem> items = shoppingCart.getItems();
        for (CartItem item : items) {
            tableModel.addRow(new Object[]{item.getProduct().getName(), item.getProduct().getCategory(), item.getProduct().getPrice(), item.getQuantity()});
        }
    }

    private Product getProductByName(String name) {
        for (CartItem item : shoppingCart.getItems()) {
            if (item.getProduct().getName().equals(name)) {
                return item.getProduct();
            }
        }
        return null;
    }
}