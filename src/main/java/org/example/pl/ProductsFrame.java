package org.example.pl;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ProductsFrame extends JFrame {
    private List<Product> products;
    private JTable productsTable;
    private DefaultTableModel tableModel;
    private ShoppingCart shoppingCart;

    public ProductsFrame() {
        setTitle("Products");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        products = loadProducts(); // Load products from a data source
        shoppingCart = new ShoppingCart(); // Initialize shopping cart

        JPanel productsPanel = new JPanel();
        productsPanel.setBackground(new Color(173, 216, 230)); // Light blue background color
        productsPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Search fields
        gbc.gridx = 0;
        gbc.gridy = 0;
        productsPanel.add(new JLabel("Search by Name:"), gbc);

        gbc.gridx = 1;
        JTextField nameField = new JTextField(15);
        productsPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        productsPanel.add(new JLabel("Search by Category:"), gbc);

        gbc.gridx = 1;
        JTextField categoryField = new JTextField(15);
        productsPanel.add(categoryField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        productsPanel.add(new JLabel("Search by Price Range:"), gbc);

        gbc.gridx = 1;
        JTextField priceRangeField = new JTextField(15);
        productsPanel.add(priceRangeField, gbc);

        // Search button
        gbc.gridx = 1;
        gbc.gridy = 3;
        JButton searchButton = new JButton("Search");
        productsPanel.add(searchButton, gbc);

        // Products table
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        tableModel = new DefaultTableModel(new Object[]{"Name", "Category", "Price"}, 0);
        productsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(productsTable);
        productsPanel.add(scrollPane, gbc);

        // Display all products initially
        displayProducts(products);

        // Add action listener to search button
        searchButton.addActionListener(e -> {
            String name = nameField.getText();
            String category = categoryField.getText();
            String priceRange = priceRangeField.getText();
            List<Product> filteredProducts = searchProducts(name, category, priceRange);
            displayProducts(filteredProducts);
        });

        // Add mouse listener to products table for product details view
        productsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = productsTable.rowAtPoint(evt.getPoint());
                if (row >= 0) {
                    String selectedProductName = (String) productsTable.getValueAt(row, 0);
                    Product selectedProduct = getProductByName(selectedProductName);
                    if (selectedProduct != null) {
                        new ProductDetailsFrame(selectedProduct, shoppingCart);
                    }
                }
            }
        });

        // Shopping cart button
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        JButton cartButton = new JButton("View Cart");
        cartButton.addActionListener(e -> new ShoppingCartFrame(shoppingCart));
        productsPanel.add(cartButton, gbc);

        add(productsPanel);
        setVisible(true);
    }

    private List<Product> loadProducts() {
        // Load products from a data source (e.g., database, file, etc.)
        // For demonstration purposes, we'll create a few sample products
        List<Product> products = new ArrayList<>();
        products.add(new Product("Laptop", "Electronics", 1000, "High-performance laptop", true));
        products.add(new Product("Smartphone", "Electronics", 800, "Latest model smartphone", true));
        products.add(new Product("T-shirt", "Fashion", 20, "Comfortable cotton t-shirt", true));
        products.add(new Product("Blender", "Home Appliances", 50, "High-speed blender", false));
        products.add(new Product("Headphones", "Electronics", 150, "Noise-cancelling headphones", true));
        products.add(new Product("Coffee Maker", "Home Appliances", 80, "Automatic coffee maker", true));
        products.add(new Product("Sneakers", "Fashion", 60, "Stylish and comfortable sneakers", true));
        products.add(new Product("Microwave", "Home Appliances", 120, "Compact microwave oven", false));
        return products;
    }

    private List<Product> searchProducts(String name, String category, String priceRange) {
        // Filter products based on search criteria
        List<Product> filteredProducts = new ArrayList<>();
        for (Product product : products) {
            boolean matches = true;
            if (!name.isEmpty() && !product.getName().toLowerCase().contains(name.toLowerCase())) {
                matches = false;
            }
            if (!category.isEmpty() && !product.getCategory().toLowerCase().contains(category.toLowerCase())) {
                matches = false;
            }
            if (!priceRange.isEmpty()) {
                String[] range = priceRange.split("-");
                if (range.length == 2) {
                    try {
                        double minPrice = Double.parseDouble(range[0].trim());
                        double maxPrice = Double.parseDouble(range[1].trim());
                        if (product.getPrice() < minPrice || product.getPrice() > maxPrice) {
                            matches = false;
                        }
                    } catch (NumberFormatException e) {
                        matches = false;
                    }
                } else {
                    matches = false;
                }
            }
            if (matches) {
                filteredProducts.add(product);
            }
        }
        return filteredProducts;
    }

    private void displayProducts(List<Product> products) {
        tableModel.setRowCount(0); // Clear existing rows
        for (Product product : products) {
            tableModel.addRow(new Object[]{product.getName(), product.getCategory(), product.getPrice()});
        }
    }

    private Product getProductByName(String name) {
        for (Product product : products) {
            if (product.getName().equals(name)) {
                return product;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ProductsFrame::new);
    }
}

class Product {
    private String name;
    private String category;
    private double price;
    private String description;
    private boolean availability;

    public Product(String name, String category, double price, String description, boolean availability) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.description = description;
        this.availability = availability;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public boolean isAvailable() {
        return availability;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Category: " + category + ", Price: $" + price;
    }
}