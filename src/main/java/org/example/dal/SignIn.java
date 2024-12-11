package org.example.dal;

import org.example.dto.SignInDto;
import org.example.helper.UserSession;
import org.example.pl.DatabaseConnection;
import org.example.pl.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SignIn {
    private static final Logger logger = Logger.getLogger(SignIn.class.getName());
    public User signInUser(String email, String password) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT id, username, role FROM users WHERE email = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int userId = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String role = resultSet.getString("role");

                // Log fetched data
                System.out.println("Fetched userId: " + userId);
                System.out.println("Fetched username: " + username);
                System.out.println("Fetched role: " + role);

                // Return constructed User object
                return new User(userId, username, password, email, role);
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, "SigneIN operation failed.", e.getMessage());
        }
        return null;
    }

    public String getUserRole(String email) {
        String sql = "SELECT role FROM users WHERE email = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("role");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean changePassword(String email, String newPassword) {
        String sql = "UPDATE users SET password = ? WHERE email = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, newPassword);
            preparedStatement.setString(2, email);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            logger.log(Level.WARNING, "failed  to change the password.", e.getMessage());
            return false;
        }
    }

    public int getUserId(String email) {
        String sql = "SELECT id FROM users WHERE email = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, "failed fo find user.", e.getMessage());
        }
        return -1; // Return -1 or handle appropriately if user ID is not found
    }
}