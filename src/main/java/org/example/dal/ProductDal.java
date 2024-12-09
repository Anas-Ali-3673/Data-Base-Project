package org.example.dal;

import org.example.dto.ProductDto;
import org.example.pl.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductDal {
    private static final Logger logger = Logger.getLogger(ProductDal.class.getName());

    public boolean addProduct(ProductDto product) {
        String sql = "INSERT INTO products (name, description, price, stock,category,isAvailable) VALUES (?, ?, ?, ?,?,?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            if (connection.isClosed()) {
                logger.severe("Connection is closed or null.");
                return false;
            }
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.setInt(4, product.getStock());
            preparedStatement.setString(5,product.getCategory());
            preparedStatement.setBoolean(6,product.isAvailable());
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Product added successfully.");
                return true;
            } else {
                logger.warning("Product addition failed.");
                return false;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQL Exception occurred", e);
            return false;
        }
    }

    public List<ProductDto> getAllProducts() {
        String sql = "SELECT * FROM products";
        List<ProductDto> products = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            if (connection == null || connection.isClosed()) {
                logger.severe("Connection is closed or null.");
                return null;
            }
            while (resultSet.next()) {
                ProductDto product = new ProductDto();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setDescription(resultSet.getString("description"));
                product.setPrice(resultSet.getDouble("price"));
                product.setStock(resultSet.getInt("stock"));
                product.setCategory(resultSet.getString("category"));
                product.setAvailable(resultSet.getBoolean("isAvailable"));
                products.add(product);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQL Exception occurred", e);
            return null;
        }
        return products;
    }
    public boolean updateProduct(ProductDto product) {
        String sql = "UPDATE products SET name = ?, description = ?, price = ?, stock = ?, category = ?, isAvailable = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            if (connection == null || connection.isClosed()) {
                logger.severe("Connection is closed or null.");
                return false;
            }
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.setInt(4, product.getStock());
            preparedStatement.setString(5, product.getCategory());
            preparedStatement.setBoolean(6, product.isAvailable());
            preparedStatement.setInt(7, product.getId());
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQL Exception occurred", e);
            return false;
        }
    }
    public boolean deleteProduct(int productId) {
        String sql = "DELETE FROM products WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            if (connection == null || connection.isClosed()) {
                logger.severe("Connection is closed or null.");
                return false;
            }
            preparedStatement.setInt(1, productId);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Product deleted successfully.");
                return true;
            } else {
                logger.warning("Product deletion failed.");
                return false;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQL Exception occurred", e);
            return false;
        }
    }

    public ProductDto getProductById(int productId) {
        String sql = "SELECT * FROM products WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, productId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                ProductDto product = new ProductDto();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setDescription(resultSet.getString("description"));
                product.setPrice(resultSet.getDouble("price"));
                product.setStock(resultSet.getInt("stock"));
                product.setCategory(resultSet.getString("category"));
                product.setAvailable(resultSet.getBoolean("isAvailable"));
                return product;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQL Exception occurred", e);
        }
        return null;
    }

    public List<ProductDto> searchProducts(String name, String category, Double minPrice, Double maxPrice) {
        List<ProductDto> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE 1=1";
        if (name != null && !name.isEmpty()) {
            sql += " AND name LIKE ?";
        }
        if (category != null && !category.isEmpty()) {
            sql += " AND category LIKE ?";
        }
        if (minPrice != null) {
            sql += " AND price >= ?";
        }
        if (maxPrice != null) {
            sql += " AND price <= ?";
        }

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            int paramIndex = 1;
            if (name != null && !name.isEmpty()) {
                preparedStatement.setString(paramIndex++, "%" + name + "%");
            }
            if (category != null && !category.isEmpty()) {
                preparedStatement.setString(paramIndex++, "%" + category + "%");
            }
            if (minPrice != null) {
                preparedStatement.setDouble(paramIndex++, minPrice);
            }
            if (maxPrice != null) {
                preparedStatement.setDouble(paramIndex++, maxPrice);
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ProductDto product = new ProductDto();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setDescription(resultSet.getString("description"));
                product.setPrice(resultSet.getDouble("price"));
                product.setStock(resultSet.getInt("stock"));
                product.setCategory(resultSet.getString("category"));
                product.setAvailable(resultSet.getBoolean("isAvailable"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
}