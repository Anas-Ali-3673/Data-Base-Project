package org.example.dal;

import org.example.dto.ProductDto;
import org.example.pl.DatabaseConnection;

import javax.swing.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    private Map<ProductDto, Integer> cartItems;

    public ShoppingCart() {
        cartItems = new HashMap<>();
        loadCartFromDatabase();
    }

    public void addProduct(ProductDto product, int quantity) {
        if (cartItems.containsKey(product)) {
            JOptionPane.showMessageDialog(null, "Product is already in the cart.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        cartItems.put(product, quantity);
        saveCartToDatabase();
    }

    public void removeProduct(ProductDto product) {
        cartItems.remove(product);
        saveCartToDatabase();
    }
    public void updateProductQuantity(String productName, int quantity) {
        for (Map.Entry<ProductDto, Integer> entry : cartItems.entrySet()) {
            if (entry.getKey().getName().equals(productName)) {
                entry.setValue(quantity);
                break;
            }
        }
        saveCartToDatabase();
    }

    public double getTotalCost() {
        double totalCost = 0.0;
        for (Map.Entry<ProductDto, Integer> entry : cartItems.entrySet()) {
            totalCost += entry.getKey().getPrice() * entry.getValue();
        }
        return totalCost;
    }

    public Map<ProductDto, Integer> getCartItems() {
        return cartItems;
    }

    public void removeProductByName(String productName) {
        cartItems.entrySet().removeIf(entry -> entry.getKey().getName().equals(productName));
        saveCartToDatabase();
    }

    private void saveCartToDatabase() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection == null || connection.isClosed()) {
                throw new SQLException("Connection is closed or null.");
            }

            // Clear existing cart items in the database
            String clearSql = "DELETE FROM cart_items";
            try (PreparedStatement clearStmt = connection.prepareStatement(clearSql)) {
                clearStmt.executeUpdate();
            }

            // Insert current cart items into the database
            String insertSql = "INSERT INTO cart_items (product_id, quantity) VALUES (?, ?)";
            try (PreparedStatement insertStmt = connection.prepareStatement(insertSql)) {
                for (Map.Entry<ProductDto, Integer> entry : cartItems.entrySet()) {
                    insertStmt.setInt(1, entry.getKey().getId());
                    insertStmt.setInt(2, entry.getValue());
                    insertStmt.addBatch();
                }
                insertStmt.executeBatch();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadCartFromDatabase() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection == null || connection.isClosed()) {
                throw new SQLException("Connection is closed or null.");
            }

            String sql = "SELECT p.id, p.name, p.description, p.price, p.stock, ci.quantity " +
                         "FROM cart_items ci " +
                         "JOIN products p ON ci.product_id = p.id";
            try (PreparedStatement stmt = connection.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ProductDto product = new ProductDto();
                    product.setId(rs.getInt("id"));
                    product.setName(rs.getString("name"));
                    product.setDescription(rs.getString("description"));
                    product.setPrice(rs.getDouble("price"));
                    product.setStock(rs.getInt("stock"));
                    int quantity = rs.getInt("quantity");
                    cartItems.put(product, quantity);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clearCart() {
        cartItems.clear();
        saveCartToDatabase();
    }
}