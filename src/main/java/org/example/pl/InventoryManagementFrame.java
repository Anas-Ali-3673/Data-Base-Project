package org.example.pl;

import org.example.bll.ProductManager;
import org.example.dal.ProductDal;
import org.example.dto.ProductDto;
import org.example.dto.SignUpDto;
import org.example.dal.ShoppingCart;
import org.example.helper.UserSession;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryManagementFrame extends JFrame {
    private ProductManager productManager;
    private JTable productTable;
    private DefaultTableModel tableModel;
    private String userRole;
    private ShoppingCart shoppingCart;
    private JTextField nameField;
    private JTextField categoryField;
    private JTextField minPriceField;
    private JTextField maxPriceField;
    private int userId;
    private String username;
    private Map<String, SignUpDto> users;
    private JLabel messageLabel;

    public InventoryManagementFrame(ProductManager productManager, String userRole, int userId,String username, Map<String, SignUpDto> users, JLabel messageLabel) {
        this.productManager = productManager;
        this.userRole = userRole;
        this.userId = userId;
        this.shoppingCart = new ShoppingCart();
        this.username = username;
        this.users = users;
        this.messageLabel = messageLabel;

        setTitle("Inventory Management");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set modern look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        initComponents();
        displayInventoryItems();
    }

    private void initComponents() {
        // Main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(mainPanel);
    
       // Top Panel for Back Button
JPanel topPanel = new JPanel(new BorderLayout());
topPanel.setOpaque(false); // Ensure transparency if needed

// Back Button Panel
JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
backButtonPanel.setOpaque(false); // Transparent background

JButton backButton = new JButton("Back");
styleButton(backButton, new Color(128, 128, 128), Color.BLACK);
backButton.addActionListener(e -> goBackToHomepage());

// Ensure the button is added to the panel
backButtonPanel.add(backButton);
topPanel.add(backButtonPanel, BorderLayout.WEST); // Add backButtonPanel to topPanel

// Add topPanel to the mainPanel
mainPanel.add(topPanel, BorderLayout.NORTH); // Make sure it's added to the NORTH


    
        // Table Section
        String[] columnNames = {"ID", "Name", "Description", "Price", "Stock", "Category", "Available"};
        tableModel = new DefaultTableModel(columnNames, 0);
        productTable = new JTable(tableModel);
        productTable.setFillsViewportHeight(true);
        productTable.setRowHeight(25);
    
        JScrollPane scrollPane = new JScrollPane(productTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Product Inventory"));
        mainPanel.add(scrollPane, BorderLayout.CENTER);
    
        // Search Section
JPanel searchPanel = new JPanel(new GridLayout(2, 4, 10, 10));
searchPanel.setBorder(BorderFactory.createTitledBorder("Search Products"));

nameField = new JTextField();
categoryField = new JTextField();
minPriceField = new JTextField();
maxPriceField = new JTextField();

searchPanel.add(new JLabel("Name:"));
searchPanel.add(nameField);
searchPanel.add(new JLabel("Category:"));
searchPanel.add(categoryField);
searchPanel.add(new JLabel("Min Price:"));
searchPanel.add(minPriceField);
searchPanel.add(new JLabel("Max Price:"));
searchPanel.add(maxPriceField);

JButton searchButton = new JButton("Search");
styleButton(searchButton, new Color(70, 130, 180), Color.BLUE);

searchButton.addActionListener(e -> searchProducts());

// Search button panel to hold the search button
JPanel searchButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
searchButtonPanel.add(searchButton);

// // Back button panel to hold the back button
// JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));  // Align to left
// backButtonPanel.setOpaque(false); // Ensure transparency if needed

// backButton is already defined and added to backButtonPanel earlier

// Wrapping the search and back button panels
JPanel searchWrapperPanel = new JPanel(new BorderLayout());
searchWrapperPanel.add(searchPanel, BorderLayout.CENTER);
searchWrapperPanel.add(searchButtonPanel, BorderLayout.SOUTH);
searchWrapperPanel.add(backButtonPanel, BorderLayout.NORTH); // Add backButtonPanel to the top

mainPanel.add(searchWrapperPanel, BorderLayout.NORTH);

    
        // Button Section
        JPanel buttonPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Actions"));
    
        JButton addButton = new JButton("Add Product");
        JButton updateButton = new JButton("Update Product");
        JButton deleteButton = new JButton("Delete Product");
        JButton addToCartButton = new JButton("Add to Cart");
        JButton viewCartButton = new JButton("View Cart");
        JButton viewDetailsButton = new JButton("View Details");
    
        // Improved Button Styling
        styleButton(addButton, new Color(46, 204, 113), Color.BLUE);
        styleButton(updateButton, new Color(241, 196, 15), Color.BLUE);
        styleButton(deleteButton, new Color(231, 76, 60), Color.BLUE);
        styleButton(addToCartButton, new Color(52, 152, 219), Color.BLUE);
        styleButton(viewCartButton, new Color(155, 89, 182), Color.BLUE);
        styleButton(viewDetailsButton, new Color(26, 188, 156), Color.BLUE);
    
        // Add buttons based on role
        if ("manager".equalsIgnoreCase(userRole)) {
            buttonPanel.add(addButton);
            buttonPanel.add(updateButton);
            buttonPanel.add(deleteButton);
        }
    
        buttonPanel.add(addToCartButton);
        buttonPanel.add(viewCartButton);
        buttonPanel.add(viewDetailsButton);
    
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    
        // Button Actions
        addButton.addActionListener(e -> addProduct());
        updateButton.addActionListener(e -> updateProduct());
        deleteButton.addActionListener(e -> deleteProduct());
        addToCartButton.addActionListener(e -> addToCart());
        viewCartButton.addActionListener(e -> viewCart());
        viewDetailsButton.addActionListener(e -> viewProductDetails());
    }
    
    
    private void styleButton(JButton button, Color bgColor, Color fgColor) {
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14)); // Bold button text
    }

    private void displayInventoryItems() {
        List<ProductDto> products = productManager.getAllProducts();
        if (products == null) return;

        tableModel.setRowCount(0); // Clear existing rows
        for (ProductDto product : products) {
            Object[] row = {product.getId(), product.getName(), product.getDescription(), product.getPrice(),
                    product.getStock(), product.getCategory(), product.isAvailable() ? "Yes" : "No"};
            tableModel.addRow(row);
        }
    }

    private void goBackToHomepage() {
        dispose(); // Close current frame
        SwingUtilities.invokeLater(() -> new HomePageFrame(userRole, username, userId, users, messageLabel).setVisible(true)); // Open homepage
    }

    private void searchProducts() {
        String name = nameField.getText().trim();
        String category = categoryField.getText().trim();
        Double minPrice = parseDouble(minPriceField.getText().trim());
        Double maxPrice = parseDouble(maxPriceField.getText().trim());

        List<ProductDto> products = productManager.searchProducts(name, category, minPrice, maxPrice);
        if (products != null) {
            tableModel.setRowCount(0);
            for (ProductDto product : products) {
                Object[] row = {product.getId(), product.getName(), product.getDescription(), product.getPrice(),
                        product.getStock(), product.getCategory(), product.isAvailable() ? "Yes" : "No"};
                tableModel.addRow(row);
            }
        }
    }

    private Double parseDouble(String value) {
        try {
            return value.isEmpty() ? null : Double.parseDouble(value);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid price input.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    private void addProduct() {
        JTextField nameField = new JTextField(20);
        JTextField descriptionField = new JTextField(20);
        JTextField priceField = new JTextField(20);
        JTextField stockField = new JTextField(20);
        JTextField categoryField = new JTextField(20);
        JTextField isAvailableField = new JTextField(20);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Description:"));
        panel.add(descriptionField);
        panel.add(new JLabel("Price:"));
        panel.add(priceField);
        panel.add(new JLabel("Stock:"));
        panel.add(stockField);
        panel.add(new JLabel("Category:"));
        panel.add(categoryField);
        panel.add(new JLabel("Is Available:"));
        panel.add(isAvailableField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add Product", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText();
                String description = descriptionField.getText();
                double price = Double.parseDouble(priceField.getText());
                int stock = Integer.parseInt(stockField.getText());
                String category = categoryField.getText();
                boolean isAvailable = Boolean.parseBoolean(isAvailableField.getText());

                ProductDto product = new ProductDto();
                product.setName(name);
                product.setDescription(description);
                product.setPrice(price);
                product.setStock(stock);
                product.setCategory(category);
                product.setAvailable(isAvailable);

                if (productManager.addProduct(product)) {
                    displayInventoryItems();
                    JOptionPane.showMessageDialog(this, "Product added successfully.", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add product.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid input for price or stock.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateProduct() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow >= 0) {
            int productId = (int) tableModel.getValueAt(selectedRow, 0);
            String currentName = (String) tableModel.getValueAt(selectedRow, 1);
            String currentDescription = (String) tableModel.getValueAt(selectedRow, 2);
            double currentPrice = (double) tableModel.getValueAt(selectedRow, 3);
            int currentStock = (int) tableModel.getValueAt(selectedRow, 4);
            String currentCategory = (String) tableModel.getValueAt(selectedRow, 5);
            boolean currentIsAvailable = "Yes".equals(tableModel.getValueAt(selectedRow, 6));

            JTextField nameField = new JTextField(currentName, 20);
            JTextField descriptionField = new JTextField(currentDescription, 20);
            JTextField priceField = new JTextField(String.valueOf(currentPrice), 20);
            JTextField stockField = new JTextField(String.valueOf(currentStock), 20);
            JTextField categoryField = new JTextField(currentCategory, 20);
            JTextField isAvailableField = new JTextField(String.valueOf(currentIsAvailable), 20);

            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("Name:"));
            panel.add(nameField);
            panel.add(new JLabel("Description:"));
            panel.add(descriptionField);
            panel.add(new JLabel("Price:"));
            panel.add(priceField);
            panel.add(new JLabel("Stock:"));
            panel.add(stockField);
            panel.add(new JLabel("Category:"));
            panel.add(categoryField);
            panel.add(new JLabel("Is Available:"));
            panel.add(isAvailableField);

            int result = JOptionPane.showConfirmDialog(null, panel, "Update Product", JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    String name = nameField.getText();
                    String description = descriptionField.getText();
                    double price = Double.parseDouble(priceField.getText());
                    int stock = Integer.parseInt(stockField.getText());
                    String category = categoryField.getText();
                    boolean isAvailable = Boolean.parseBoolean(isAvailableField.getText());

                    ProductDto product = new ProductDto();
                    product.setId(productId);
                    product.setName(name);
                    product.setDescription(description);
                    product.setPrice(price);
                    product.setStock(stock);
                    product.setCategory(category);
                    product.setAvailable(isAvailable);

                    if (productManager.updateProduct(product)) {
                        displayInventoryItems();
                        JOptionPane.showMessageDialog(this, "Product updated successfully.", "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to update product.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Invalid input for price or stock.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a product to update.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteProduct() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow >= 0) {
            int productId = (int) tableModel.getValueAt(selectedRow, 0);
            productManager.deleteProduct(productId);
            displayInventoryItems();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a product to delete.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addToCart() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow >= 0) {
            int productId = (int) tableModel.getValueAt(selectedRow, 0);
            ProductDto product = productManager.getProductById(productId);
            if (product != null) {
                shoppingCart.addProduct(product, 1);
                JOptionPane.showMessageDialog(this, "Product added to cart.", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add product to cart.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a product to add to cart.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewCart() {
        JFrame cartFrame = new JFrame("Shopping Cart");
        cartFrame.setSize(400, 300);
        cartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        cartFrame.setLocationRelativeTo(null);
        cartFrame.add(new ShoppingCartPanel(shoppingCart, cartFrame, userId));
        cartFrame.setVisible(true);
    }

    private void viewProductDetails() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow >= 0) {
            int productId = (int) tableModel.getValueAt(selectedRow, 0);
            ProductDto product = productManager.getProductById(productId);
            if (product != null) {
                new ProductDetailsDialog(this, product, shoppingCart).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to retrieve product details.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a product to view details.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UserSession userSession = UserSession.getInstance();
            int userId = userSession.getUserId();
            String username = userSession.getUsername();
            ProductManager productManager = new ProductManager(new ProductDal());
            Map<String, SignUpDto> users = new HashMap<>();
            JLabel messageLabel = new JLabel();
            new InventoryManagementFrame(productManager, "manager", userId, username, users, messageLabel).setVisible(true); // Replace "manager" with the actual user role
        });
    }
}