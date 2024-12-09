package org.example.dal;

import org.example.dto.SignUpDto;
import org.example.pl.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SignUp {
    private static final Logger logger = Logger.getLogger(SignUp.class.getName());

    public boolean registerUser(SignUpDto signUpDto) {
        String sql = "INSERT INTO users (username, password, email, role) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            if (connection == null || connection.isClosed()) {
                logger.severe("Connection is closed or null.");
                return false;
            }
            preparedStatement.setString(1, signUpDto.getUsername());
            preparedStatement.setString(2, signUpDto.getPassword());
            preparedStatement.setString(3, signUpDto.getEmail());
            preparedStatement.setString(4, signUpDto.getRole());
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("User registered successfully.");
                return true;
            } else {
                logger.warning("User registration failed.");
                return false;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQL Exception occurred", e);
            return false;
        }
    }
    public int getUserId(String username) {
    String sql = "SELECT id FROM users WHERE username = ?";
    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("id");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return -1; // Return -1 or handle appropriately if user ID is not found
}
}