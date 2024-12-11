package org.example.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.example.dto.PaymentDto;
import org.example.pl.DatabaseConnection;

public class PaymentDal {
    public boolean isUserExists(int userId) {
        String sql = "SELECT id FROM users WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void savePaymentToDatabase(PaymentDto paymentDto) {
        String sql = "INSERT INTO payments (user_id, payment_method, account_number, total_amount) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, paymentDto.getUserId());
            preparedStatement.setString(2, paymentDto.getPaymentMethod());
            preparedStatement.setString(3, paymentDto.getAccountNumber());
            preparedStatement.setDouble(4, paymentDto.getTotalAmount());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}