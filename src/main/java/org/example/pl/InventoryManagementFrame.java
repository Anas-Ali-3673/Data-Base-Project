package org.example.pl;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class InventoryManagementFrame extends JFrame {
    private List<Product> products;
    private JTable inventoryTable;
    private DefaultTableModel tableModel;

    public InventoryManagementFrame(List<Product> products) {
        this.products = products;

        setTitle("Inventory Management");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel inventoryPanel = new JPanel();
        inventoryPanel.setBackground(new Color(173, 216, 230)); // Light blue background color
        inventoryPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Inventory table
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        tableModel = new DefaultTableModel(new Object[]{"Name", "Category", "Price", "Stock"}, 0);
        inventoryTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(inventoryTable);
        inventoryPanel.add(scrollPane, gbc);

        // Display inventory items
        displayInventoryItems();

        // Add product button
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        JButton addButton = new JButton("Add Product");
        addButton.addActionListener(e -> addProduct());
        inventoryPanel.add(addButton, gbc);

        // Update product button
        gbc.gridx = 1;
        gbc.gridy = 1;
        JButton updateButton = new JButton("Update Product");
        updateButton.addActionListener(e -> updateProduct());
        inventoryPanel.add(updateButton, gbc);

        // Remove product button
        gbc.gridx = 0;
        gbc.gridy = 2;
        JButton removeButton = new JButton("Remove Product");
        removeButton.addActionListener(e -> removeProduct());
        inventoryPanel.add(removeButton, gbc);

        add(inventoryPanel);
        setVisible(true);
    }

    private void displayInventoryItems() {
        tableModel.setRowCount(0); // Clear existing rows
        for (Product product : products) {
            tableModel.addRow(new Object[]{product.getName(), product.getCategory(), product.getPrice(), product.getStock()});
        }
    }

    private void addProduct() {
        JTextField nameField = new JTextField(15);
        JTextField categoryField = new JTextField(15);
        JTextField priceField = new JTextField(15);
        JTextField stockField = new JTextField(15);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        panel.add(nameField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Category:"), gbc);
        gbc.gridx = 1;
        panel.add(categoryField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Price:"), gbc);
        gbc.gridx = 1;
        panel.add(priceField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Stock:"), gbc);
        gbc.gridx = 1;
        panel.add(stockField, gbc);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add Product", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            String category = categoryField.getText();
            double price = Double.parseDouble(priceField.getText());
            int stock = Integer.parseInt(stockField.getText());
            products.add(new Product(name, category, price, "New product", true, stock));
            displayInventoryItems();
        }
    }

    private void updateProduct() {
        int selectedRow = inventoryTable.getSelectedRow();
        if (selectedRow >= 0) {
            Product product = products.get(selectedRow);

            JTextField nameField = new JTextField(product.getName(), 15);
            JTextField categoryField = new JTextField(product.getCategory(), 15);
            JTextField priceField = new JTextField(String.valueOf(product.getPrice()), 15);
            JTextField stockField = new JTextField(String.valueOf(product.getStock()), 15);

            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.gridx = 0;
            gbc.gridy = 0;
            panel.add(new JLabel("Name:"), gbc);
            gbc.gridx = 1;
            panel.add(nameField, gbc);
            gbc.gridx = 0;
            gbc.gridy = 1;
            panel.add(new JLabel("Category:"), gbc);
            gbc.gridx = 1;
            panel.add(categoryField, gbc);
            gbc.gridx = 0;
            gbc.gridy = 2;
            panel.add(new JLabel("Price:"), gbc);
            gbc.gridx = 1;
            panel.add(priceField, gbc);
            gbc.gridx = 0;
            gbc.gridy = 3;
            panel.add(new JLabel("Stock:"), gbc);
            gbc.gridx = 1;
            panel.add(stockField, gbc);

            int result = JOptionPane.showConfirmDialog(this, panel, "Update Product", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                product.setName(nameField.getText());
                product.setCategory(categoryField.getText());
                product.setPrice(Double.parseDouble(priceField.getText()));
                product.setStock(Integer.parseInt(stockField.getText()));
                displayInventoryItems();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a product to update.");
        }
    }

    private void removeProduct() {
        int selectedRow = inventoryTable.getSelectedRow();
        if (selectedRow >= 0) {
            products.remove(selectedRow);
            displayInventoryItems();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a product to remove.");
        }
    }
}