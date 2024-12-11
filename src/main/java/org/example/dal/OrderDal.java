package org.example.dal;

import org.example.dto.Order;
import org.example.dto.OrderItem;
import org.example.dto.OrderStatus;
import org.example.pl.DatabaseConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDal {

    public boolean saveOrder(Order order) {
        String orderSql = "INSERT INTO orders (user_id, order_date, total_amount, status) VALUES (?, ?, ?, ?)";
        String orderItemSql = "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection == null || connection.isClosed()) {
                throw new SQLException("Connection is closed or null.");
            }

            // Save order
            try (PreparedStatement orderStmt = connection.prepareStatement(orderSql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                orderStmt.setInt(1, order.getUserId());
                orderStmt.setDate(2, new Date(order.getOrderDate().getTime()));
                orderStmt.setDouble(3, order.getTotalAmount());
                orderStmt.setString(4, order.getStatus().name());
                int rowsAffected = orderStmt.executeUpdate();
                if (rowsAffected == 0) {
                    throw new SQLException("Creating order failed, no rows affected.");
                }

                // Get the generated order ID
                try (ResultSet generatedKeys = orderStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        order.setOrderId(generatedKeys.getInt(1));
                    } else {
                        throw new SQLException("Creating order failed, no ID obtained.");
                    }
                }
            }

            // Save order items
            try (PreparedStatement orderItemStmt = connection.prepareStatement(orderItemSql)) {
                for (OrderItem item : order.getOrderItems()) {
                    orderItemStmt.setInt(1, order.getOrderId());
                    orderItemStmt.setInt(2, item.getProductId());
                    orderItemStmt.setInt(3, item.getQuantity());
                    orderItemStmt.setDouble(4, item.getPrice());
                    orderItemStmt.addBatch();
                }
                orderItemStmt.executeBatch();
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Order> getOrdersByUserId(int userId) {
        String sql = "SELECT * FROM orders WHERE user_id = ?";
        List<Order> orders = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int orderId = resultSet.getInt("id");
                Date orderDate = resultSet.getDate("order_date");
                double totalAmount = resultSet.getDouble("total_amount");
                OrderStatus status = OrderStatus.valueOf(resultSet.getString("status"));
                List<OrderItem> orderItems = getOrderItemsByOrderId(orderId);
                Order order = new Order(userId, orderDate, totalAmount, orderItems, status);
                order.setOrderId(orderId); // Ensure order ID is set correctly
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public boolean updateOrderStatus(int orderId, OrderStatus status) {
        String sql = "UPDATE orders SET status = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, status.name());
            preparedStatement.setInt(2, orderId);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<OrderItem> getOrderItemsByOrderId(int orderId) {
        String sql = "SELECT * FROM order_items WHERE order_id = ?";
        List<OrderItem> orderItems = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, orderId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int productId = resultSet.getInt("product_id");
                int quantity = resultSet.getInt("quantity");
                double price = resultSet.getDouble("price");
                orderItems.add(new OrderItem(productId, quantity, price));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderItems;
    }
}