package org.example.dal;

import org.example.dto.Feedback;
import org.example.pl.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FeedbackDal {

    public boolean saveFeedback(Feedback feedback) {
        String sql = "INSERT INTO feedback (product_id, customer_name, comments, rating) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, feedback.getProductId());
            preparedStatement.setString(2, feedback.getCustomerName());
            preparedStatement.setString(3, feedback.getComments());
            preparedStatement.setInt(4, feedback.getRating());
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}